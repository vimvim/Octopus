package octorise.repo.octopus

import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.{Qualifier, Autowired}
import org.springframework.transaction.annotation.{Propagation, Transactional}

import octorise.repo.octopus.models.Node
import octorise.repo.octopus.services.NodeService
import octorise.repo.octopus.repositories.NodesRepo
import octorise.repo.octopus.schema.NodeTypesRegister


/**
 *
 */
@Component()
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
class NodeApiFacade {

  @Autowired
  @Qualifier("nodesRepo")
  var nodesRepo:NodesRepo[Node] =_

  @Autowired
  var typesRegister:NodeTypesRegister =_

  def findById[T <: Node](id:Int, handler:(T, NodeService[T], NodesRepo[T]) => Unit = (node:T, nodeService:NodeService[T], nodesRepo:NodesRepo[T])=>{}):Option[T] = {

    findAndHandle[T](() => {
      nodesRepo.find(id)
    }, handler)
  }

  def findBySlug[T <: Node](parent:Node, slug:String, handler:(T, NodeService[T], NodesRepo[T]) => Unit = (node:T, nodeService:NodeService[T], nodesRepo:NodesRepo[T])=>{}):Option[T] = {

    findAndHandle[T](() => {
      nodesRepo.findBySlug(parent, slug)
    }, handler)
  }

  def findByPath[T <: Node](parent:Node, path:String, handler:(T, NodeService[T], NodesRepo[T]) => Unit = (node:T, nodeService:NodeService[T], nodesRepo:NodesRepo[T])=>{}):Option[T] = {

    findAndHandle[T](() => {
      nodesRepo.findByPath(parent, path)
    }, handler)
  }

  private def findAndHandle[T <: Node](finder: ()=>Option[Node], handler:(T, NodeService[T], NodesRepo[T]) => Unit):Option[T] = {

    finder() match {

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
