package octorise.repo

import scala.concurrent.Future

/**
 *
 */
trait Repository[T] {

  def get(location:Location):Either[Response, Future[Response]]

}
