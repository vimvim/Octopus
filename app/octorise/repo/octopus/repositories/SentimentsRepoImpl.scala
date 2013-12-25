package octorise.repo.octopus.repositories

import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.{Propagation, Transactional}

import octorise.repo.octopus.models.Sentiment

/**
 *
 */
@Repository("sentimentsRepo")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
class SentimentsRepoImpl extends AbstractSentimentsRepo[Sentiment] {

}
