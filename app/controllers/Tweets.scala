package controllers

import play.api._
import play.api.mvc._
import play.api.Play.current
import spring.SpringContextHolder
import viewmodels.ListPresenter
import models.Tweet
import play.api.cache.Cached
import scala.concurrent.Future
import org.springframework.transaction.{TransactionDefinition, PlatformTransactionManager}
import org.springframework.transaction.support.DefaultTransactionDefinition

// import org.springframework.transaction.annotation.{Propagation, Transactional}
import repositories.TweetsRepo

case class Transactional[A](action: Action[A]) extends Action[A] {

  def apply(request: Request[A]): Future[SimpleResult] = {

    Logger.info("Calling action")

    val transactionManager = SpringContextHolder.getContext.getBean(classOf[PlatformTransactionManager])
    val transactionDef = new DefaultTransactionDefinition()
    // explicitly setting the transaction name is something that can only be done programmatically
    transactionDef.setName("SomeTxName")
    transactionDef.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED)

    val transactionStatus = transactionManager.getTransaction(transactionDef)
    // execute your business logic here
    // try }
    //} catch (MyException ex) {
    //  txManager.rollback(status)
    //  throw ex;
    // }
    // txManager.commit(status)

    val res = action(request)

    transactionManager.commit(transactionStatus)
    res
  }

  lazy val parser = action.parser
}

/**
 *
 */
@org.springframework.stereotype.Controller
class Tweets extends Controller  {

  /**
   * List of the tweets
   * @return
   */

  def index = Transactional { Action {

    // This is for testing only
    val repo = SpringContextHolder.getContext.getBean("tweetsRepo", classOf[TweetsRepo])
    val res = repo.fetch(0, 10)
    val tweet1 = res.head
    val socialUser = tweet1.socialUser
    val attributes = socialUser.getAttributes

    val presenter = SpringContextHolder.getContext.getBean("presenter.tweets.full", classOf[ListPresenter[Tweet, _]])
    val tweets = presenter.get(0, 10)

    Ok(views.html.index("Your new application is ready."))
  }}

  def index2 = Action {
    Ok(views.html.index("Your new application is ready."))
  }

}
