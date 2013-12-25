package octorise.repo.octopus.repositories

import octorise.repo.octopus.models.Tweet

/**
 *
 */
trait TweetsRepo extends SentimentsRepo[Tweet] {

  def findByTweetId(tweetId: String): Option[Tweet]
}
