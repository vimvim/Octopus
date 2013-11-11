package ui.services

import com.vaadin.server.Page
import com.vaadin.ui.Notification
import com.vaadin.ui.Upload
import models.SocialUser
import models.Tweet
import org.codehaus.jackson.JsonNode
import org.codehaus.jackson.map.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import repositories.SocialUsersRepo
import repositories.TweetsRepo
import scala.Function1
import scala.runtime.AbstractFunction1
import scala.runtime.BoxedUnit
import services.SocialUserService
import services.TweetService
import java.io._

/**
 * Handle tweets uploaded in the JSOn from from topsy service.
 */
@Component
@Scope("session")
class TopsyTweetsHandler extends Upload.Receiver with Upload.SucceededListener {

  var tweetService: TweetService = null
  var tweetsRepo: TweetsRepo = null
  var socialUserService: SocialUserService = null
  var socialUsersRepo: SocialUsersRepo = null
  var file: File = null

  @Autowired def this(
                       @Qualifier("tweetService") tweetService: TweetService,
                       @Qualifier("tweetsRepo") tweetsRepo: TweetsRepo,
                       @Qualifier("socialUserService") socialUserService: SocialUserService,
                       @Qualifier("socialUsersRepo") socialUsersRepo: SocialUsersRepo) {

    this()

    this.tweetService = tweetService
    this.tweetsRepo = tweetsRepo
    this.socialUserService = socialUserService
    this.socialUsersRepo = socialUsersRepo
  }

  def receiveUpload(filename: String, mimeType: String): OutputStream = {

    var fos: FileOutputStream = null

    try {

      file = new File("/tmp/uploads/" + filename)
      fos = new FileOutputStream(file)

    } catch {

      case e: FileNotFoundException => {
        new Notification("Could not open file<br/>", e.getMessage, Notification.Type.ERROR_MESSAGE).show(Page.getCurrent)
        null
      }
    }

    fos
  }

  def uploadSucceeded(event: Upload.SucceededEvent) {

    val mapper: ObjectMapper = new ObjectMapper

    try {

      val fileReader: BufferedReader = new BufferedReader(new FileReader(file))
      var rootNode: JsonNode = null

      while ( {
        rootNode = mapper.readTree(fileReader)
        rootNode
      } != null) {

        val tweetId: String = rootNode.path("id_str").getTextValue
        val tweetText: String = rootNode.path("text").getTextValue

        val entities: JsonNode = rootNode.path("entities")
        val user: JsonNode = rootNode.path("user")
        val topsy: JsonNode = rootNode.path("topsy")

        val userID: String = user.path("id").getTextValue
        val screenName: String = user.path("screen_name").getTextValue
        val userName: String = user.path("name").getTextValue

        socialUsersRepo.findBySchemaAttr("twitter", "id", userID) match {

          case Some(socialUser) => {
            addTweet(socialUser, tweetId, tweetText)
          }

          case None => {
            addTweet(
              socialUserService.create( (socialUser: SocialUser) => {

                socialUser.setName(userName)

                socialUser.setAttribute("twitter", "id", userID)
                socialUser.setAttribute("twitter", "screen_name", screenName)
                socialUser.setAttribute("twitter", "name", userName)
              } ),
              tweetId, tweetText
            )
          }
        }
      }
    } catch {
      case ex: Exception => {
      }
    }
  }

  def addTweet(socialUser: SocialUser, tweetId: String, tweetText: String) {

    tweetsRepo.findByTweetId(tweetId) match {

      case Some(tweet) =>
      case None => tweetService.create((tweet: Tweet) => {
        tweet.setTweetId(tweetId)
        tweet.setText(tweetText)
        tweet.setSocialUser(socialUser)
      })
    }
  }
}