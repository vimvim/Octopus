package controllers

import play.api._
import play.api.mvc._
import spring.SpringContextHolder
import viewmodels.ListPresenter
import models.Tweet
import play.db.jpa.Transactional

// import org.springframework.transaction.annotation.{Propagation, Transactional}
import repositories.TweetsRepo

/**
 *
 */
@org.springframework.stereotype.Controller
@Transactional
class Tweets extends Controller  {

  /**
   * List of the tweets
   * @return
   */

  @Transactional(value="default", readOnly = false)
  def index = Action {

    // This is for testing only
    val repo = SpringContextHolder.getContext.getBean("tweetsRepo", classOf[TweetsRepo])
    val res = repo.fetch(0, 10)
    val tweet1 = res.head
    val socialUser = tweet1.socialUser
    val attributes = socialUser.getAttributes

    val presenter = SpringContextHolder.getContext.getBean("presenter.tweets.full", classOf[ListPresenter[Tweet, _]])
    val tweets = presenter.get(0, 10)

    Ok(views.html.index("Your new application is ready."))
  }

  @Transactional(value="default", readOnly = false)
  def index2 = Action {
    Ok(views.html.index("Your new application is ready."))
  }

}
