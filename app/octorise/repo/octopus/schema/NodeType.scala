package octorise.repo.octopus.schema


import octorise.repo.octopus.models.Node
import octorise.repo.octopus.services.NodeService
import octorise.repo.octopus.repositories.NodesRepo

/**
 *
 */
class NodeType[T <: Node] (
   val nodeClass: Class[T],
   val name: String,
   val baseType: NodeType[_],
   val service: NodeService[T],
   val repo: NodesRepo[T]
)
