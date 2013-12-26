package octorise.repo.octopus.schema

import spring.SpringContextHolder
import octorise.repo.octopus.models.Node


/**
 * Service holds registered node types.
 *
 */
class NodeTypesRegister {

  var typesByName = Map[String, NodeType[_]]()

  var typesByClass = Map[Class[_], NodeType[_]]()


  // TODO: Temporary solution. Refactor later.
  registerNodeTypeBean("nodeType.Node")
  registerNodeTypeBean("nodeType.Content")

  /*
  def registerType[T <: Node](typeName:String, extendType:String, applicableSchemas: Set[SchemaDescriptor], service: NodeService[T], repo: NodesRepo[T]) = {

  }
  */

  def registerNodeType(nodeType:NodeType[_]) {

    val typeName = nodeType.name

    typesByName.get(nodeType.name) match {
      case Some(foundType) => throw new Exception(s"Type already registered: $typeName ")
      case None =>
        typesByName = typesByName + (typeName -> nodeType)
    }
  }

  def registerNodeTypeBean(beanTypeName:String) {
    val nodeType = SpringContextHolder.getContext.getBean("nodeType.Content")
    registerNodeType(nodeType.asInstanceOf[NodeType[_]])
  }

  def getNodeType[T <: Node](node:T):Option[NodeType[T]] = {
    None
  }

  def getNodeType(typeName:String):Option[NodeType[_]] = {
    typesByName.get(typeName)
  }

}
