package ui.services

import java.io._
import actors.{FileBatchLoad, FileBatchLoader, TopsyTweetsParser}
import akka.actor.{ActorRef, Props}

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

import com.vaadin.server.Page
import com.vaadin.ui.Notification
import com.vaadin.ui.Upload


/**
 * Handle tweets uploaded in the JSOn from from topsy service.
 */
@Component
@Scope("session")
class TopsyTweetsHandler extends Upload.Receiver with Upload.SucceededListener {

  // @Autowired
  // @Qualifier("topsyTweetsImporter")
  // var tweetsImporter: TopsyTweetsParser =_

  @Autowired
  @Qualifier("fileBatchLoader")
  var loader: ActorRef =_

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

    // val loader = Akka.system.actorOf(Props.apply(Class[SpringActorProducer], Class[FileBatchLoader]))
    loader ! FileBatchLoad(file.getAbsolutePath, "topsyTweetsParser")


    // Akka.system.actorOf(Props.create(SpringActorProducer.class, TopsyTweetsLoader.class, "myActor"))

    // try {

    //  tweetsImporter.loadTweets(file)
    //} catch {

    //  case e: Exception => {
    //    new Notification("Could not import file", e.getMessage, Notification.Type.ERROR_MESSAGE).show(Page.getCurrent)
    //  }
    // }
  }
}