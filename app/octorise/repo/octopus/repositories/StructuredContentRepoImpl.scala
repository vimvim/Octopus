package octorise.repo.octopus.repositories

import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.{Propagation, Transactional}

import octorise.repo.octopus.models.StructuredContent

/**
 *
 */
@Repository("structuredContentRepo")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
class StructuredContentRepoImpl extends ContentRepoImpl[StructuredContent] with StructuredContentRepo {

}
