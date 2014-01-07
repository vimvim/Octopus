package octorise.repo.octopus.repositories

import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.{Propagation, Transactional}

import octorise.repo.octopus.models.TextContent

/**
 *
 */
@Repository("textContentRepo")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
class TextContentRepoImpl extends ContentRepoImpl[TextContent] with ContentRepo[TextContent] {

}
