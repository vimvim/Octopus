package octorise.presenter

import scala.concurrent.Future
import octorise.repo.Repository

/**
 *
 */
trait Renderer[T] {
    def render(repository:Repository, label:String, content:AnyRef):Either[RenderedContent, Future[RenderedContent]]
}
