package viewmodels

import octorise.repo.octopus.models.Tweet
import octorise.viewmodel.ViewModel

/**
 *
 */
class TweetShort(tweet: Tweet) extends ViewModel[Tweet]  {

  val id = tweet.id

  val text = tweet.text

}
