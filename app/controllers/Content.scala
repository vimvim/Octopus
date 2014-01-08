package controllers

import scala.concurrent.duration._

import akka.actor.ActorRef
import akka.pattern.ask
import akka.util.Timeout

import play.api.mvc.{SimpleResult, Action, Controller}
import org.springframework.beans.factory.annotation.{Qualifier, Autowired}

import octorise.repo.{AbsoluteLocation, Repository}
import octorise.presenter.PresentContent
import org.springframework.context.annotation.Lazy


/**
 * Content controller.
 * Responsible for rendering content.
 */
@org.springframework.stereotype.Controller
@Lazy
class Content extends Controller {

  @Autowired
  @Qualifier("presenter")
  var presenter: ActorRef =_

  @Autowired
  @Qualifier("presenter")
  var rootRepository: Repository[_] =_

  def renderContent = Action.async {

    implicit val timeout = Timeout(3 seconds)

    (presenter ? PresentContent("root", rootRepository, AbsoluteLocation("/test"))).mapTo[SimpleResult]

    // Ok(views.html.index("Application is ready."))
  }
}
