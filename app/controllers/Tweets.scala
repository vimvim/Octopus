package controllers

import scala.concurrent.Future

import play.api._
import play.api.mvc._
import play.api.Play.current
import play.api.cache.Cached

import org.springframework.transaction.{TransactionDefinition, PlatformTransactionManager}
import org.springframework.transaction.support.DefaultTransactionDefinition

import spring.SpringContextHolder

import octorise.repo.octopus.models.{Node, Tweet}
import octorise.viewmodel.ListPresenter
import octorise.repo.octopus.repositories.{NodesRepo, ContentRepo, TweetsRepo}


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

  def index3 = Transactional { Action {

    // This is for testing only
    /*
    val contentRepo = SpringContextHolder.getContext.getBean("contentRepo", classOf[ContentRepo])
    val nodesRepo = SpringContextHolder.getContext.getBean("nodesRepo", classOf[NodesRepo[Node]])

    val node = nodesRepo.find(28336)
    val content = contentRepo.find(28336)

    val contents = contentRepo.findByParent(null)
    val nodes = nodesRepo.findByParent(null)

    val content2 = contentRepo.findBySlug(null, "test")
    val node2 = nodesRepo.findBySlug(null, "test")
    */

    Ok(views.html.index("Your new application is ready."))
  }}

}
