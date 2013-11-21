package viewmodels

import models.Tweet

/**
 *
 */
class TweetShort(tweet: Tweet) extends ViewModel[Tweet]  {

  val id = tweet.id

  val text = tweet.text

}
