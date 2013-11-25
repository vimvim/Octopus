package viewmodels

import org.springframework.transaction.annotation.{Propagation, Transactional}
import org.springframework.beans.factory.annotation.{Qualifier, Autowired}
import org.springframework.stereotype.{Service, Component}

import models.Tweet
import repositories.TweetsRepo


/**
 * Present list of the tweets in the full format
 *
 * @param repo    Reference to the tweets repo
 */
@Service("presenter.tweets.full")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
class TweetsFullPresenter @Autowired()(@Autowired @Qualifier("tweetsRepo") repo: TweetsRepo) extends ListPresenter[Tweet, TweetFull](repo)

/**
 * Present list of the tweets in the short format
 *
 * @param repo    Reference to the tweets repo
 */
@Service("presenter.tweets.short")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
class TweetsShortPresenter @Autowired()(@Autowired @Qualifier("tweetsRepo") repo: TweetsRepo) extends ListPresenter[Tweet, TweetFull](repo)

