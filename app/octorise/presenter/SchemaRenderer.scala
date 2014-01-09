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
 * Render structured content which is consist of the subcontent referred ( or contained ) in the attributes of the content schema.
 */
@Service("schemaRenderer")
@Lazy
class SchemaRenderer extends StructuredRenderer[StructuredContent] {

  /**
   * Render passed content
   *
   * @param repository      Repository to which content belongs
   * @param label           Content label. Used for identifying content in pages and also in the render response
   * @param topContent         Content to render
   * @return
   */

  def render(repository:Repository[StructuredContent], label:String, topContent: StructuredContent): Either[RenderedContent, Future[RenderedContent]] = {

    log.debug(s"Render $label $topContent")

    val contentSchema = topContent.contentSchema
    val subContent = topContent.attrs(contentSchema)

    val futures = subContent.map((entry)=>{

      val attrName = entry._1
      val attr = entry._2

      log.debug(s"$label Request subcontent:$attrName ")

      // TODO: Check attribute type here and dispatch presentation request only for attributes referenced to nodes.
      // TODO: For simple type attributes - render in-place and create already completed future

      implicit val timeout = Timeout(60 seconds)
      val renderingFuture = presenter ? PresentContent(attrName, repository, ReferencedLocation(topContent, contentSchema, attrName))

      (attrName, renderingFuture)
    })

    Right(renderStructured(label, topContent, futures))
  }
}
