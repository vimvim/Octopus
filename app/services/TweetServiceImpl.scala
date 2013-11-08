package services

import models.{Node, Tweet}

/**
 *
 */
class TweetServiceImpl extends AbstractSentimentService[Tweet] with TweetService {

  def create(): Tweet = {
    new Tweet
  }

}
