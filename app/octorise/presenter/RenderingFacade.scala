package octorise.presenter

import org.springframework.stereotype.Component
import octorise.repo.{ContentResponse, Repository}
import scala.concurrent.Future
import octorise.repo.octopus.models.{TextContent, StructuredContent, Content}
import spring.SpringContextHolder

/**
 * Content rendering facade. Dispatch rendering requests to appropriate registered renderers.
 */
@Component("renderingFacade")
class RenderingFacade {

  def render[T](repository: Repository[T], label: String, content: T): Either[RenderedContent, Future[RenderedContent]] = {

    var renderer:Renderer[T] = null

    content match {
      case content:TextContent => renderer = SpringContextHolder.getContext.getBean("textRenderer").asInstanceOf[Renderer[T]]
      case content:StructuredContent => renderer = SpringContextHolder.getContext.getBean("structuredRenderer").asInstanceOf[Renderer[T]]
    }

    renderer.render(repository, label, content)
  }

}
