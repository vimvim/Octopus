package repositories

import models.Tweet

/**
 *
 */
trait TweetsRepo extends SentimentsRepo[Tweet] {

  def findByTweetId(tweetId: String): Option
}
