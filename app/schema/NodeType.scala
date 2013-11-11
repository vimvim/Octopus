package schema

import models.Node
import services.NodeService
import repositories.NodesRepo

/**
 *
 */
class NodeType[T <: Node] {

  var name: String =_

  var extend: Set[NodeType] =_

  var applicableSchema: Set[SchemaDescriptor] =_

  var service: NodeService[T] =_

  var repo: NodesRepo[T] =_

}
