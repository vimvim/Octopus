package octorise.repo.octopus.repositories

import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.{Propagation, Transactional}

import octorise.repo.octopus.models.Content

/**
 *
 */
@Repository("contentRepo")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
class ContentRepoImpl extends AbstractNodesRepo[Content] with ContentRepo  {

}
