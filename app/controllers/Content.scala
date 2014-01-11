package controllers

import scala.concurrent.duration._

import akka.actor.ActorRef
import akka.pattern.ask
import akka.util.Timeout

import org.springframework.beans.factory.annotation.{Qualifier, Autowired}
import org.springframework.context.annotation.Lazy

import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc.{SimpleResult, Action, Controller}

import octorise.repo.{AbsoluteLocation, Repository}
import octorise.presenter.{RenderedContent, PresentContent}



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
  @Qualifier("octopusRepository")
  var rootRepository: Repository[_] =_

  def renderContent(url:String) = Action.async {

    implicit val timeout = Timeout(3 seconds)

    (presenter ? PresentContent("root", rootRepository, AbsoluteLocation("/test"))).mapTo[RenderedContent].map(renderResponse=>{
      Ok(renderResponse.content)
    })

    // Ok(views.html.index("Application is ready."))
  }
}
