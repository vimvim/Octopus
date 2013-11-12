package services

import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.{Qualifier, Autowired}
import repositories.{SocialUsersRepo, TweetsRepo}
import java.io.{FileReader, BufferedReader, File}
import org.codehaus.jackson.map.ObjectMapper
import org.codehaus.jackson.JsonNode
import models.{Tweet, SocialUser}

/**
 *
 */
@Component("topsyTweetsImporter")
class TopsyTweetsImporter {

  var tweetService: TweetService = null
  var tweetsRepo: TweetsRepo = null
  var socialUserService: SocialUserService = null
  var socialUsersRepo: SocialUsersRepo = null

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

  def loadTweets(file: File) {

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

        socialUsersRepo.findOneBySchemaAttrValue("twitter", "id", userID) match {

          case Some(socialUser) => {
            addTweet(socialUser, tweetId, tweetText)
          }

          case None => {
            addTweet(
              socialUserService.create((socialUser: SocialUser) => {

                socialUser.setName(userName)

                socialUser.setAttribute("twitter", "id", userID)
                socialUser.setAttribute("twitter", "screen_name", screenName)
                socialUser.setAttribute("twitter", "name", userName)
              }),
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
