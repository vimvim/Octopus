package octorise.repo.octopus

import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.{Qualifier, Autowired}

import octorise.repo.octopus.models.Node
import octorise.repo.octopus.services.NodeService
import octorise.repo.octopus.repositories.NodesRepo
import octorise.repo.octopus.schema.NodeTypesRegister


/**
 *
 */
@Component()
class NodeApiFacade {

  @Autowired
  @Qualifier("nodesRepo")
  var nodesRepo:NodesRepo[Node] =_

  @Autowired
  var typesRegister:NodeTypesRegister =_

  def findBySlug[T <: Node](parent:Node, slug:String, handler:(T, NodeService[T], NodesRepo[T]) => Unit):Option[T] = {

    nodesRepo.findBySlug(parent, slug) match {

      case Some(node) =>

        val foundNode = node.asInstanceOf[T]

        typesRegister.getNodeType(foundNode) match {
          case Some(nodeType) =>

            handler(foundNode, nodeType.service, nodeType.repo)
            Some(foundNode)

          case None => None
        }

      case None => None
    }
  }
}
