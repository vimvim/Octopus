package octorise.repo.octopus.schema


import octorise.repo.octopus.models.Node
import octorise.repo.octopus.services.NodeService
import octorise.repo.octopus.repositories.NodesRepo

/**
 *
 */
class NodeType[T <: Node] {

  var name: String =_

  var extend: Set[NodeType[Node]] =_

  var applicableSchema: Set[SchemaDescriptor] =_

  var service: NodeService[T] =_

  var repo: NodesRepo[T] =_

}
