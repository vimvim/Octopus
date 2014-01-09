package octorise.repo.octopus

import scala.concurrent.Future

import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.Autowired

import octorise.repo._
import octorise.repo.octopus.models.{StructuredContent, Node, Content}
import octorise.repo.RelativeLocation
import octorise.repo.Response

/**
 *
 */
@Service("octopusRepository")
class OctopusRepository extends Repository[Node] {

  @Autowired
  var nodeApiFacade: NodeApiFacade = _


  def get(location:Location):Either[Response, Future[Response]] = {

    location match {

      case absoluteLocation:AbsoluteLocation =>
        // Content referenced by absolute path ( consist of the slug's )

        nodeApiFacade.findByPath[Node](null, absoluteLocation.path) match {
          case Some(node) => Left(ContentResponse[Node]("", node))
          case None => Left(NotFoundResponse())
        }


      case referencedLocation:ReferencedLocation[StructuredContent] =>
        // Content node referenced by attribute in the parent node

        val parentNode = referencedLocation.node
        val refAttrName = referencedLocation.attrName

        val content = parentNode.attr[Node](parentNode.contentSchema, refAttrName)

        Left(ContentResponse("", content))


      case relativeLocation:RelativeLocation[Node] =>
        // Content node is referenced as are child node ( referenced by slug ) in the parent node.

        val parentNode = relativeLocation.parent
        val path = relativeLocation.path

        nodeApiFacade.findBySlug[Node](parentNode, path) match {
          case Some(node) => Left(ContentResponse("", node))
          case None => Left(NotFoundResponse())
        }
    }
  }
}
