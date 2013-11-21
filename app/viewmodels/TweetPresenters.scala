package viewmodels

import repositories.TweetsRepo
import models.Tweet


/**
 * Present list of the tweets in the full format
 *
 * @param repo    Reference to the tweets repo
 */
class TweetsFullPresenter(repo: TweetsRepo) extends ListPresenter[Tweet, TweetFull](repo)

/**
 * Present list of the tweets in the short format
 *
 * @param repo    Reference to the tweets repo
 */
class TweetsShortPresenter(repo: TweetsRepo) extends ListPresenter[Tweet, TweetFull](repo)

