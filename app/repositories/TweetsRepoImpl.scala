package repositories

import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.{Propagation, Transactional}

import models.Tweet

/**
 *
 */
@Repository("tweetsRepo")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
class TweetsRepoImpl extends AbstractSentimentsRepo[Tweet] with TweetsRepo {

  def findByTweetId(tweetId: String): Option[Tweet] = {

    val query = entityManager.createQuery("SELECT tweet FROM Tweet tweet WHERE tweet.tweetId=:tweetId")

    val res = query.getResultList

    if (res.size()>0) {
      Some(res.get(0).asInstanceOf[Tweet])
    } else {
      None
    }
  }
}
