package octorise.presenter

import scala.concurrent.Future
import org.springframework.stereotype.Service

import octorise.repo.octopus.models.TextContent
import octorise.repo.Repository

/**
 *
 */
@Service("textRenderer")
class TextRenderer extends Renderer[TextContent] {

  /**
   * Render passed content
   *
   * @param repository      Repository to which content belongs
   * @param label           Content label. Used for identifying content in pages and also in the render response
   * @param content         Content to render
   * @return
   */
  def render(repository: Repository, label: String, content: TextContent): Either[RenderedContent, Future[RenderedContent]] = {
    Left(RenderedContent(label, "", content.content))
  }
}
