package controllers

import play.api.mvc.{SimpleResult, Action, Controller}
import org.springframework.beans.factory.annotation.{Qualifier, Autowired}
import akka.actor.ActorRef
import akka.pattern.ask
import octorise.repo.Repository
import octorise.presenter.{Location, PresentContent}

/**
 * Content controller.
 * Responsible for rendering content.
 */
@org.springframework.stereotype.Controller
class Content extends Controller {

  @Autowired
  @Qualifier("presenter")
  var presenter: ActorRef =_

  @Autowired
  @Qualifier("presenter")
  var rootRepository: Repository =_

  def renderContent = Action.async {

    (presenter ? PresentContent("root", Location(rootRepository, "/test"))).mapTo[SimpleResult]

    // Ok(views.html.index("Application is ready."))
  }
}
