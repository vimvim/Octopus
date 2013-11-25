package controllers

import play.api._
import play.api.mvc._
import spring.SpringContextHolder
import viewmodels.ListPresenter
import models.Tweet
import org.springframework.transaction.annotation.{Propagation, Transactional}
import repositories.TweetsRepo

/**
 *
 */
@org.springframework.stereotype.Controller
class Tweets extends Controller  {

  /**
   * List of the tweets
   * @return
   */

  @play.db.jpa.Transactional
  def index = Action {

    // This is for testing only
    val repo = SpringContextHolder.getContext.getBean("tweetsRepo", classOf[TweetsRepo])
    repo.totalCount

    val presenter = SpringContextHolder.getContext.getBean("presenter.tweets.full", classOf[ListPresenter[Tweet, _]])
    val tweets = presenter.get(0, 10)

    Ok(views.html.index("Your new application is ready."))
  }

}
