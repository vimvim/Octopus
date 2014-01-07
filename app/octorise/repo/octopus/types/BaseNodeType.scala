package octorise.repo.octopus.types

import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.{Qualifier, Autowired}

import octorise.repo.octopus.repositories.NodesRepo
import octorise.repo.octopus.models.Node
import octorise.repo.octopus.schema.NodeType

@Component("nodeType.Node")
class BaseNodeType @Autowired()(

  @Autowired @Qualifier("nodesRepo")
  repo: NodesRepo[Node]

) extends NodeType[Node](classOf[Node], "node", null, null, repo)


