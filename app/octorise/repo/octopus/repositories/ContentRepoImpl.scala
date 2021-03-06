package octorise.repo.octopus.repositories

import octorise.repo.octopus.models.Content
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.{Propagation, Transactional}

/**
 *
 */
@Repository("contentRepo")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
class ContentRepoImpl extends AbstractContentRepo[Content] {

}
