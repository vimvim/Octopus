package octorise.repo.octopus.services

import org.springframework.stereotype.{Component, Service}
import org.springframework.beans.factory.annotation.{Qualifier, Autowired}

import octorise.repo.octopus.models.Node
import octorise.repo.octopus.repositories.NodesRepo
import octorise.repo.octopus.schema.NodeType


@Component("nodeType.Node")
class BaseNodeType @Autowired()(

  @Autowired @Qualifier("nodesRepo")
  repo: NodesRepo[Node]

) extends NodeType[Node]("node", null, null, repo)



/**
 * TODO: Not make sense. Needs to be removed.
 */
@Service("nodeService")
class NodeServiceImpl extends AbstractNodeService[Node] {

  @Autowired
  @Qualifier("nodesRepo")
  var _repo: NodesRepo[Node] = _

  def repo(): NodesRepo[Node] = {
      _repo
  }

  protected def createEntity(): Node = {
    throw new NotImplementedError("Create method is not implemented for general node type")
  }
}
