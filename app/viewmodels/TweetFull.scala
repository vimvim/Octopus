package viewmodels

import models.Tweet

/**
 *
 */
class TweetFull(tweet: Tweet) extends ViewModel[Tweet] {

  val id = tweet.id

  val tweetId = tweet.tweetId

  val twitterUserId = value(tweet.socialUser.attr[Int]("twitter.user", "id"), 0)

  val screenName = value(tweet.socialUser.attr[String]("twitter.user", "screen_name"), "")

  val userName = value(tweet.socialUser.attr[String]("twitter.user", "name"), "")

  val text = tweet.text

}
