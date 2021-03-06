package octorise.presenter



import scala._
import scala.concurrent.duration._
import scala.concurrent.duration.FiniteDuration
import scala.concurrent.{Await, Promise, Future}
import scala.concurrent.Future

import akka.actor._
import akka.pattern.{ after, ask, pipe }
import akka.routing.RoundRobinRouter
import akka.util.Timeout
import akka.actor.Actor

import org.springframework.beans.factory.annotation.{Autowired, Configurable}

import octorise.repo._
import octorise.repo.octopus.models.Content
import octorise.repo.ContentResponse
import octorise.repo.RedirectResponse
import org.springframework.stereotype.Component
import org.springframework.context.annotation.{Lazy, Scope}
import play.api.Logger


/**
 * Ask presenter to present ( render ) content
 *
 * @param label       Content name in the parent container. Useful for identifying subcontent.
 * @param location    Content location
 */
case class PresentContent[T](label: String, repository:Repository[T], location:Location)

sealed class RenderResponse()

case class RenderedContent(label:String, mimeType:String, content:String) extends RenderResponse

case class RenderTimeout(label:String) extends RenderResponse

/**
 *
 */
@Component("Presenter.Actor")
@Scope("prototype")
@Lazy
class Presenter extends Actor {

  lazy val log = Logger("application." + this.getClass.getName)

  import context.system
  import context.dispatcher

  @Autowired
  var renderingFacade:RenderingFacade = _

  def receive: Actor.Receive = {

    case PresentContent(label, repository, location) =>

      log.debug(s"$label: Present content")

      renderContent(label, repository, location) match {
        case Left(renderedContent) => sender ! renderedContent
        case Right(future) => future pipeTo sender
      }
  }

  private def renderContent[T](label: String, repository:Repository[T], location:Location):Either[RenderedContent, Future[RenderedContent]] = {

    getContent(label, repository, location) match {

      case Left(response) => render(repository, label, response)

      case Right(getFuture) => Right(getFuture flatMap {
        response=>

          render(repository, label, response) match {
            case Left(renderedContent) => Future.successful[RenderedContent](renderedContent)
            case Right(renderFuture) => renderFuture
          }
      })
    }
  }

  private def getContent[T](label: String, repository:Repository[T], location:Location): Either[Response, Future[Response]] = {

    def handleAnswer(answer: Either[Response, Future[Response]]):Either[Response, Future[Response]] = {

      answer match {

        case Left(response) =>

          response match {

            case redirectResponse:RedirectResponse =>
              log.debug(s"$label: GetContent: Redirected to ${redirectResponse.location}")
              handleAnswer(redirectResponse.repository.get(redirectResponse.location))

            case notFoundResponse:NotFoundResponse =>
              log.debug(s"$label: GetContent: Not found")
              Left(notFoundResponse)

            case contentResponse:ContentResponse[T] =>
              log.debug(s"$label: GetContent: Content found")
              Left(contentResponse)
          }

        case Right(future) =>

          // TODO: There will be problem if content repository will return Future[RedirectResponse]]

          log.debug(s"$label: GetContent: Future received")
          Right(future.asInstanceOf[Future[ContentResponse[T]]])
      }
    }

    handleAnswer(repository.get(location))
  }

  private def render[T](repository: Repository[T], label: String, response: Response): Either[RenderedContent, Future[RenderedContent]] = {

    response match {
      case contentResponse: ContentResponse[T] => renderingFacade.render(repository, label, contentResponse.content)
      case notFoundResponse: NotFoundResponse => Left(RenderedContent(label, "", "Not found"))
    }
  }
}
