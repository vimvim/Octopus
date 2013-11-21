package controllers

import play.api._
import play.api.mvc._
import spring.SpringContextHolder
import viewmodels.ListPresenter
import models.Tweet

/**
 *
 */
@org.springframework.stereotype.Controller
class Tweets extends Controller  {

  /**
   * List of the tweets
   * @return
   */
  def index = Action {

    val presenter = SpringContextHolder.getContext().getBean("presenter.tweet.full", classOf[ListPresenter[Tweet, _]])
    val tweets = presenter.get(0, 10)

    Ok(views.html.index("Your new application is ready."))
  }

}
