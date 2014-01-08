package octorise.presenter

import scala.concurrent.Future
import octorise.repo.Repository

/**
 * Common interface to the content renderers
 */
trait Renderer[T] {

  /**
   * Render passed content
   *
   * @param repository      Repository to which content belongs
   * @param label           Content label. Used for identifying content in pages and also in the render response
   * @param content         Content to render
   * @return
   */
  def render(repository:Repository[T], label:String, content:T):Either[RenderedContent, Future[RenderedContent]]
}
