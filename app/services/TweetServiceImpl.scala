package services

import models.{User, Node, Tweet}
import org.springframework.stereotype.Service
import repositories.{TweetsRepo, UsersRepo, NodesRepo}
import org.springframework.beans.factory.annotation.{Qualifier, Autowired}

/**
 *
 */
@Service("tweetService")
class TweetServiceImpl extends AbstractSentimentService[Tweet] with TweetService {

  @Autowired
  @Qualifier("tweetsRepo")
  var _repo: TweetsRepo = _

  def repo(): NodesRepo[Tweet] = _repo

  def create(initializer: (Tweet) => Unit): Tweet = new Tweet()
}
