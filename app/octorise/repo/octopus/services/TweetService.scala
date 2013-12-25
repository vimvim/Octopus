package octorise.repo.octopus.services

import octorise.repo.octopus.models.{Tweet, SocialUser}

/**
 *
 */
trait TweetService extends SentimentService[Tweet] {

  def createTweet(userId: Int, screenName: String, userName: String, tweetId: String, tweetText: String)

  def createTweet(socialUser: SocialUser, tweetId: String, tweetText: String)

}
