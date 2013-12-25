package octorise.repo.octopus

import scala.concurrent.Future
import octorise.repo.{Response, Repository}

/**
 *
 */
class OctopusRepository extends Repository {

  def get(path: String): Either[Response, Future[Response]] = {

  }

}
