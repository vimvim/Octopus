package octorise.repo.octopus.repositories

import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.{Propagation, Transactional}

import octorise.repo.octopus.models.Node

/**
 *
 */
@Repository("nodesRepo")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
class NodesRepoImpl extends AbstractNodesRepo[Node] {

}
