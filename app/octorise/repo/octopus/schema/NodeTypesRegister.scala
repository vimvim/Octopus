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

  types.toArray.foreach({nodeType=>
    registerNodeType(nodeType.asInstanceOf[NodeType[_]])
  })

  def registerNodeType(nodeType:NodeType[_]) {

    val typeName = nodeType.name

    typesByName.get(nodeType.name) match {
      case Some(foundType) => throw new Exception(s"Type already registered: $typeName ")
      case None => typesByName = typesByName + (typeName -> nodeType)
    }

    val nodeClass = nodeType.nodeClass

    typesByClass.get(nodeClass) match {
      case Some(foundType) => throw new Exception(s"Type already registered: $nodeClass ")
      case None => typesByClass = typesByClass + (nodeClass -> nodeType)
    }
  }

  def getNodeType[T <: Node](node:T):Option[NodeType[T]] = {

    def findNodeType(nodeClass: Class[_]):Option[NodeType[T]] = {

      typesByClass.get(nodeClass) match {
        case Some(nodeType) => Some(nodeType.asInstanceOf[NodeType[T]])
        case None =>
          val superClass = nodeClass.getSuperclass
          if (superClass.equals(classOf[Object])) {
            None
          } else {
            findNodeType(superClass)
          }
      }
    }

    findNodeType(node.getClass)
  }

  def getNodeType[T <: Node](typeName:String):Option[NodeType[T]] = {
    typesByName.get(typeName).asInstanceOf[Option[NodeType[T]]]
  }

}
