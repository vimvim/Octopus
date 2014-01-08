package octorise.presenter

import octorise.repo.octopus.models.Content
import scala.concurrent.Future
import akka.util.Timeout
import scala.concurrent.duration.FiniteDuration

import scala._
import scala.concurrent.duration._
import scala.concurrent.duration.FiniteDuration
import scala.concurrent.{Await, Promise, Future}

import akka.actor._
import akka.pattern.{ after, ask, pipe }
import akka.routing.RoundRobinRouter
import akka.util.Timeout
import org.springframework.beans.factory.annotation.{Qualifier, Autowired}
import octorise.repo.{Repository, RelativeLocation}
import play.api.libs.concurrent.Akka
import octorise.repo.octopus.ReferencedLocation
import org.springframework.stereotype.Service
import play.api.Play.current


/**
 * Renderer for structured content ( content which is consist of the several sub nodes )
 */
@Service("structuredRenderer")
class StructuredRenderer extends Renderer[Content] {

  @Autowired
  @Qualifier("presenter")
  var presenter: ActorRef =_

  /**
   * Render passed content
   *
   * @param repository      Repository to which content belongs
   * @param label           Content label. Used for identifying content in pages and also in the render response
   * @param content         Content to render
   * @return
   */
  def render(repository:Repository[Content], label:String, content: Content): Either[RenderedContent, Future[RenderedContent]] = {

    // TODO: Think about storing schema name in the special attribute of the Content
    val subContent = content.attrs("content")

    val futures = subContent.foldLeft(List[Future[RenderResponse]]()) {
      (list, entry) =>

      // For every operation we needs to have single future ith will group another two together
      // first is the rendering future and second is timeout fallback future
      // THINK ABOUT SETUP ANOTHER MAPPING FOR RENDERING FUTURE IN THE FALLBACK TIMEOUT FUTURE.
      // WE CAN ADD SOME CODE WHICH WILL HANDLE RESULT AND SEND IT ASYNC ( OVER WEBSOCKET ) TO BROWSER
      // AFTER PAGE IS RENDERED.

      // TODO: How we can grab result of the late future ??  RESOLVED BELOW \/

      // https://github.com/twitter/util/blob/master/util-core/src/main/scala/com/twitter/util/Future.scala#L331-336
      // https://gist.github.com/viktorklang/4488970

      // Think about create Promise for answer from presenter future. Handler for answer will setup value for promise.
      // In the timeout we can check status of the Promise or associated future and if it is not completed than
      // setup handler for it which will grab result and send it to the AsyncRenderingCollector actor which will
      // handle rendered content ( store or send to the websocket ).

      // Another possible implementation - always send rendering results to the special actor, also send
      // timeout message. If timeout message will arrive before content message - content will be scheduled
      // for delivery to the client over websocket.

        // import Akka.system.

        implicit val ec = Akka.system.dispatcher

        // import context.dispatcher
        implicit val timeout = Timeout(60 seconds)

        val subContentLabel = entry._1
        val subContentRef = entry._2.getName

        // Tracer.log(s"Request subcontent:$subContentLabel url:$subContentUrl")

        // TODO: Check attribute type here and dispatch presentation request only for attributes referenced to nodes.
        // TODO: For simple type attributes - render in-place

        val renderingFuture = presenter ? PresentContent(subContentLabel, repository, ReferencedLocation(content, "content", subContentRef))

        @volatile var timedOut = false

        list :+ (Future firstCompletedOf Seq(
          renderingFuture map {response=>

            if (timedOut) {
              // lateDeliveryActor ! response
              // Tracer.log(s"$subContentLabel Rendered too late. Send for async delivery. ")
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
    }

    // import context.system
    // import context.dispatcher

    implicit val ec = Akka.system.dispatcher

    // And finally we will put rendered subcontent into cells inside parent layout
    Right(Future.fold(futures)(Map[String, String]()) {
      (contentMap, response: RenderResponse) =>

        // Tracer.log(s"$label: Got response for subcontent $response")

        response match {
          case RenderedContent(subContentLabel, mimeType, data) => contentMap ++ Map((subContentLabel, data))
          case RenderTimeout(subContentLabel) => contentMap ++ Map((subContentLabel, "Content rendering timeout"))
        }

    } map {
      contentMap =>
      // Render all gathered content ( will render template is the real system here )
        renderFlat(label, content, contentMap)
    })
  }

  private def renderFlat(label: String, content:Content, subContentMap:Map[String,String]):RenderedContent = {

    // Tracer.log(s"$label: Final rendering")

    RenderedContent(label, "text", subContentMap.foldLeft("") {
      (data, entry) =>

        val contentLabel = entry._1
        val contentData = entry._2

        data.concat(s"<div id='$contentLabel'>$contentData</div>")
    })
  }

}
