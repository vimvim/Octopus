package octopus

import scala.concurrent.Future

sealed class Response

case class RedirectResponse(repository:Repository, path:String) extends Response

case class ContentResponse[T](kind:String, content:T) extends Response

/**
 *
 */
trait Repository {

  def get(path:String):Either[Response, Future[Response]]

}
