package octorise.presenter


import scala.concurrent.Future
import akka.util.Timeout
import scala.concurrent.duration.FiniteDuration

import scala._
import scala.concurrent.duration._
import scala.concurrent.duration.FiniteDuration
import scala.concurrent.{Await, Promise, Future}

import akka.actor._
import akka.pattern.{ after, ask, pipe }
import akka.util.Timeout

import org.springframework.beans.factory.annotation.{Qualifier, Autowired}
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Service

import octorise.repo.{Repository, RelativeLocation}
import octorise.repo.octopus.ReferencedLocation
import octorise.repo.octopus.models.Content

import play.api.Play.current
import play.api.libs.concurrent.Akka
import play.api.Logger


/**
 * Renderer for structured content ( content which is consist of the several sub content pieces )
 */
abstract class StructuredRenderer[ T <: Content ] extends Renderer[T] {

  lazy val log = Logger("application." + this.getClass.getName)

  @Autowired
  @Qualifier("presenter")
  var presenter: ActorRef =_

  implicit val ec = Akka.system.dispatcher

  protected def renderStructured(label:String, topContent:T, contentFutures: Map[String, Future[Any]]):Future[RenderedContent] = {

    val futures = contentFutures.map((entry)=>{

      val subContentLabel = entry._1
      val renderingFuture = entry._2

      @volatile var timedOut = false

      (Future firstCompletedOf Seq(
        renderingFuture map {response=>

          if (timedOut) {
            // lateDeliveryActor ! response
            log.debug(s"$subContentLabel Rendered too late. Send for async delivery. ")
          }

          response
        },
        after(FiniteDuration(3, "seconds"), Akka.system.scheduler) {

          Future {
            timedOut = true
            RenderTimeout(subContentLabel)
          }

          // Future successful RenderTimeout(subContentLabel)
        }
      )).mapTo[RenderResponse]
    })

    Future.fold(futures)(Map[String, String]()) {
      (contentMap, response: RenderResponse) =>

        log.debug(s"$label: Got response for subcontent $response")

        response match {
          case RenderedContent(subContentLabel, mimeType, data) => contentMap ++ Map((subContentLabel, data))
          case RenderTimeout(subContentLabel) => contentMap ++ Map((subContentLabel, "Content rendering timeout"))
        }

    } map {
      contentMap =>
      // Render all gathered content ( will render template is the real system here )
        renderFlat(label, topContent, contentMap)
    }
  }

  private def renderFlat(label: String, topContent:T, subContentMap:Map[String,String]):RenderedContent = {

    // Tracer.log(s"$label: Final rendering")

    RenderedContent(label, "text", subContentMap.foldLeft("") {
      (data, entry) =>

        val contentLabel = entry._1
        val contentData = entry._2

        data.concat(s"<div id='$contentLabel'>$contentData</div>")
    })
  }

}
