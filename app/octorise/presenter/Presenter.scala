package octorise.presenter

import akka.actor.Actor

import scala._
import scala.concurrent.duration._
import scala.concurrent.duration.FiniteDuration
import scala.concurrent.{Await, Promise, Future}

import akka.actor._
import akka.pattern.{ after, ask, pipe }
import akka.routing.RoundRobinRouter
import akka.util.Timeout

import octorise.repo._
import scala.concurrent.Future
import octorise.repo.octopus.models.Content
import octorise.repo.ContentResponse
import octorise.presenter.RenderedContent
import octorise.repo.RedirectResponse
import octorise.presenter.PresentContent
import org.springframework.beans.factory.annotation.Configurable


/**
 * Ask presenter to present ( render ) content
 *
 * @param label       Content name in the parent container. Useful for identifying subcontent.
 * @param location    Content location
 */
case class PresentContent(label: String, repository:Repository, location:Location)

sealed class RenderResponse()

case class RenderedContent(label:String, mimeType:String, content:String) extends RenderResponse

case class RenderTimeout(label:String) extends RenderResponse

/**
 *
 */
@Configurable
class Presenter extends Actor {

  def receive: Actor.Receive = {

    case PresentContent(label, repository, location) =>

      // Tracer.log(s"$label: Present content")

      // Capture sender val
      // val sender = this.sender

      import context.system
      import context.dispatcher

      renderContent(label, repository, location) match {
        case Left(renderedContent) => sender ! renderedContent
        case Right(future) => future pipeTo sender
      }


  }

  private def renderContent(label: String, repository:Repository, location:Location):Either[RenderedContent, Future[RenderedContent]] = {

    import context.system
    import context.dispatcher

    getContent(label, repository, location) match {

      case Left(contentResponse) => render(repository, label, contentResponse)

      case Right(getFuture) => Right(getFuture flatMap {
        contentResponse=>

          render(repository, label, contentResponse) match {
            case Left(renderedContent) => Future.successful[RenderedContent](renderedContent)
            case Right(renderFuture) => renderFuture
          }
      })
    }
  }

  private def getContent(label: String, repository:Repository, location:Location): Either[ContentResponse, Future[ContentResponse]] = {

    import context.system
    import context.dispatcher

    def handleAnswer(answer: Either[Response, Future[Response]]):Either[ContentResponse, Future[ContentResponse]] = {

      answer match {

        case Left(response) =>

          response match {
            case redirectResponse:RedirectResponse => handleAnswer(redirectResponse.repository.get(redirectResponse.path))
            case contentResponse:ContentResponse => Left(contentResponse)
          }

        case Right(future) =>

          // TODO: There will be problem if content repository will return Future[RedirectResponse]]

          Right(future.asInstanceOf[Future[ContentResponse]])
      }
    }

    handleAnswer(repository.get(location))
  }

  private def render(repository: Repository, label: String, content: ContentResponse): Either[RenderedContent, Future[RenderedContent]] = {

    // TODO: Select presenter using content type/kind/...

    val renderer:Renderer = null

    renderer.render(repository, label, content.content)
  }
}
