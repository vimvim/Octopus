package octorise.repo.octopus

import scala.concurrent.Future
import octorise.repo._
import octorise.repo.octopus.models.{Node, Content}
import octorise.repo.RelativeLocation

/**
 *
 */
class OctopusRepository extends Repository[Node] {

  // def get(path: String): Either[Response, Future[Response]] = {
  // TODO: This is only stub.
  //  Left(null)
  // }

  def get(location:Location):Either[Response, Future[Response]] = {

    location match {
      case absolute:AbsoluteLocation =>
      case relative:RelativeLocation[Node] =>

        val content = relative.parent.getAttributeValue[Node]("content", relative.relativePath)


    }

  }

}
