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
import services.{TopsyTweetsImporter, SocialUserService, TweetService}
import java.io._

/**
 * Handle tweets uploaded in the JSOn from from topsy service.
 */
@Component
@Scope("session")
class TopsyTweetsHandler extends Upload.Receiver with Upload.SucceededListener {

  @Autowired
  @Qualifier("topsyTweetsImporter")
  var tweetsImporter: TopsyTweetsImporter =_

  var file: File = null

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

    try {

      tweetsImporter.loadTweets(file)
    } catch {

      case e: Exception => {
        new Notification("Could not import file", e.getMessage, Notification.Type.ERROR_MESSAGE).show(Page.getCurrent)
      }
    }
  }
}