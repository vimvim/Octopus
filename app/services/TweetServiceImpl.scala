package services

import models.{SocialUser, User, Node, Tweet}
import org.springframework.stereotype.Service
import repositories.{SocialUsersRepo, TweetsRepo, UsersRepo, NodesRepo}
import org.springframework.beans.factory.annotation.{Qualifier, Autowired}
import org.springframework.transaction.annotation.{Propagation, Transactional}

/**
 *
 */
@Service("tweetService")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
class TweetServiceImpl extends AbstractSentimentService[Tweet] with TweetService {

  @Autowired
  @Qualifier("tweetsRepo")
  var _repo: TweetsRepo = _

  @Autowired
  @Qualifier("socialUserService")
  var socialUserService: SocialUserService =_

  @Autowired
  @Qualifier("socialUsersRepo")
  var socialUsersRepo: SocialUsersRepo =_

  def repo(): NodesRepo[Tweet] = _repo

  protected def createEntity(): Tweet = new Tweet()

  def createTweet(userId: Int, screenName: String, userName: String, tweetId: String, tweetText: String): Unit = {

    socialUsersRepo.findOneBySchemaAttrValue("twitter.user", "id", userId) match {

      case Some(socialUser) => {
        createTweet(socialUser, tweetId, tweetText)
      }

      case None => {
        createTweet(
          socialUserService.create((socialUser: SocialUser) => {

            socialUser.setName(userName)

            socialUser.setAttribute("twitter.user", "id", userId)
            socialUser.setAttribute("twitter.user", "screen_name", screenName)
            socialUser.setAttribute("twitter.user", "name", userName)
          }),
          tweetId, tweetText
        )
      }
    }
  }

  def createTweet(socialUser: SocialUser, tweetId: String, tweetText: String) {

    _repo.findByTweetId(tweetId) match {

      case Some(tweet) =>
      case None => create((tweet: Tweet) => {
        tweet.setTweetId(tweetId)
        tweet.setText(tweetText)
        tweet.setSocialUser(socialUser)
      })
    }
  }

}
