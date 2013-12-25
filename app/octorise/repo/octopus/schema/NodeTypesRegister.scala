package octorise.repo.octopus.schema

import octorise.repo.octopus.services.NodeService
import octorise.repo.octopus.repositories.NodesRepo
import octorise.repo.octopus.models.Node

/**
 * Service holds registered node types.
 *
 */
class NodeTypesRegister {

  var types = Map[String, NodeType[_]]()

  def registerType[T](typeName:String, extendType:String, applicableSchemas: Set[SchemaDescriptor], service: NodeService[T], repo: NodesRepo[T]) = {

  }

  def getNodeType[T <: Node](node:T):Option[NodeType[T]] = {
    None
  }

  def getNodeType(typeName:String):Option[NodeType[_]] = {
    types.get(typeName)
  }

}
