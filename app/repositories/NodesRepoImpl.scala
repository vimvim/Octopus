package repositories

import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.{Propagation, Transactional}

import models.Node

/**
 *
 */
@Repository("nodesRepo")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
class NodesRepoImpl extends AbstractNodesRepo[Node] {

}
