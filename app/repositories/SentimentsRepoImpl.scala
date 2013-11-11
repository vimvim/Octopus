package repositories

import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.{Propagation, Transactional}

import models.Sentiment

/**
 *
 */
@Repository("sentimentsRepo")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
class SentimentsRepoImpl extends AbstractSentimentsRepo[Sentiment] {

}
