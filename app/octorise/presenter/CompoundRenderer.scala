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

/**
 * Renderer for compound content ( content which is consist of the several sub nodes )
 */
class CompoundRenderer extends Renderer[Content] {

  @Autowired
  @Qualifier("presenter")
  var presenter: ActorRef =_

  def render(repository:Repository, label:String, content: Content): Either[RenderedContent, Future[RenderedContent]] = {

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

        import Akka.system

        // import context.dispatcher
        implicit val timeout = Timeout(60 seconds)

        val subContentLabel = entry._1
        val subContentRef = entry._2.getName

        // Tracer.log(s"Request subcontent:$subContentLabel url:$subContentUrl")

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

    RenderedContent(label, "text", subContentMap.foldLeft(content.content) {
      (data, entry) =>

        val contentLabel = entry._1
        val contentData = entry._2

        data.concat(s"<div id='$contentLabel'>$contentData</div>")
    })
  }

}
