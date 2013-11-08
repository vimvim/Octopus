package services

import models.{Node, Tweet}
import org.springframework.stereotype.Service

/**
 *
 */
@Service("tweetService")
class TweetServiceImpl extends AbstractSentimentService[Tweet] with TweetService {

  def create(): Tweet = {
    new Tweet
  }

}
