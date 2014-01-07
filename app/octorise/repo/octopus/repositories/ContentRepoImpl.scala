package octorise.repo.octopus.repositories

import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.{Propagation, Transactional}

import octorise.repo.octopus.models.Content

/**
 *
 */
@Repository("contentRepo")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
abstract class ContentRepoImpl[ T<: Content] extends AbstractNodesRepo[T] with ContentRepo[T] {

}
