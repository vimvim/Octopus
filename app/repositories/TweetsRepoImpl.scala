package repositories

import models.Tweet

/**
 *
 */
class TweetsRepoImpl extends AbstractSentimentsRepo[Tweet] with TweetsRepo {

  def findByTweetId(tweetId: String): Option = {


  }
}
