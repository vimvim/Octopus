package octorise.repo.octopus.schema

import org.springframework.stereotype.Component

import octorise.repo.octopus.models.Node


/**
 * Service holds registered node types.
 *
 */

class NodeTypesRegister(types:java.util.List[NodeType[_]]) {

  var typesByName = Map[String, NodeType[_]]()

  var typesByClass = Map[Class[_], NodeType[_]]()


  // TODO: Temporary solution. Refactor later.
  // registerNodeTypeBean("nodeType.Node")
  // registerNodeTypeBean("nodeType.Content")

  /*
  def registerType[T <: Node](typeName:String, extendType:String, applicableSchemas: Set[SchemaDescriptor], service: NodeService[T], repo: NodesRepo[T]) = {

  }
  */

  types.toArray.foreach({nodeType=>
    registerNodeType(nodeType.asInstanceOf[NodeType[_]])
  })

  def registerNodeType(nodeType:NodeType[_]) {

    val typeName = nodeType.name

    typesByName.get(nodeType.name) match {
      case Some(foundType) => throw new Exception(s"Type already registered: $typeName ")
      case None =>
        typesByName = typesByName + (typeName -> nodeType)
    }
  }

  /*
  def registerNodeTypeBean(beanTypeName:String) {
    val nodeType = SpringContextHolder.getContext.getBean("nodeType.Content")
    registerNodeType(nodeType.asInstanceOf[NodeType[_]])
  }
  */

  def getNodeType[T <: Node](node:T):Option[NodeType[T]] = {
    None
  }

  def getNodeType[T <: Node](typeName:String):Option[NodeType[T]] = {
    typesByName.get(typeName).asInstanceOf[Option[NodeType[T]]]
  }

}
