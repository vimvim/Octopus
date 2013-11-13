package services

import models.{User, Node, Tweet}
import org.springframework.stereotype.Service
import repositories.{TweetsRepo, UsersRepo, NodesRepo}
import org.springframework.beans.factory.annotation.{Qualifier, Autowired}
import org.springframework.transaction.annotation.{Propagation, Transactional}

/**
 *
 */
@Service("tweetService")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
class TweetServiceImpl extends AbstractSentimentService[Tweet] with TweetService {

  @Autowired
  @Qualifier("tweetsRepo")
  var _repo: TweetsRepo = _

  def repo(): NodesRepo[Tweet] = _repo

  protected def createEntity(): Tweet = new Tweet()
}
