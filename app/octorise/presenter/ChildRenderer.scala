package octorise.presenter

import octorise.repo.Repository
import octorise.repo.octopus.models.{StructuredContent, Attribute, Content}
import scala.concurrent.Future
import akka.util.Timeout
import octorise.repo.octopus.ReferencedLocation


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

import play.api.Play.current
import play.api.libs.concurrent.Akka


/**
 * Render structured content which is consist of the sub content represented by child nodes.
 */
@Service("childRenderer")
@Lazy
class ChildRenderer extends StructuredRenderer[Content] {
  
  /**
   * Render passed content
   *
   * @param repository      Repository to which content belongs
   * @param label           Content label. Used for identifying content in pages and also in the render response
   * @param topContent      Content to render
   * @return
   */
  def render(repository: Repository[Content], label: String, topContent: Content): Either[RenderedContent, Future[RenderedContent]] = {

    val childSlugs = topContent.getChildSlugs

    val futures = childSlugs.map((slug)=>{

      implicit val timeout = Timeout(60 seconds)
      val renderingFuture = presenter ? PresentContent(slug, repository, RelativeLocation(topContent, slug))

      (slug, renderingFuture)
    }).toMap

    Right(renderStructured(label, topContent, futures))
  }
}
