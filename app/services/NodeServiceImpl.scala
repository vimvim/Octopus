package services

import models.Node
import org.springframework.stereotype.Service
import repositories.NodesRepo
import org.springframework.beans.factory.annotation.{Qualifier, Autowired}

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
