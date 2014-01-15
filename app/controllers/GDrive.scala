package controllers

import scala.concurrent.duration._

import akka.actor.ActorRef
import akka.pattern.ask
import akka.util.Timeout

import org.springframework.beans.factory.annotation.{Qualifier, Autowired}
import org.springframework.context.annotation.Lazy

import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc.{SimpleResult, Action, Controller}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc.{SimpleResult, Action, Controller}

import octorise.repo.{AbsoluteLocation, Repository}
import octorise.presenter.{RenderedContent, PresentContent}
import octorise.repo.gdrive.services.GDriveService

/**
 * GDrive related functionality controller
 */
@org.springframework.stereotype.Controller
@Lazy
class GDrive extends Controller {

  @Autowired
  var gdriveService: GDriveService =_

  /**
   * Used as are callback in the Google OAuth calls
   *
   * @return
   */
  def auth(repoName: String, error: String, state: String, code: String) = Action.async {

    gdriveService.get(repoName) match {

      case Some(repo) =>

        gdriveService.authConfirm(repo, code).map(result => {

          if (result) {
            Ok(views.html.index("Authorized"))
          } else {
            Ok(views.html.index("Access denied"))
          }
        })

      // case None => Ok(views.html.index("Unknown repository"))
    }
  }

  /**
   * Simple test
   *
   * @return
   */
  def test() = Action {

    val testRepo = gdriveService.create("test");

    val authUrl = gdriveService.auth(testRepo)

    Redirect(authUrl)


    // Ok(views.html.index("Your new application is ready."))
  }

  /*
  def test2() = Action {

    gdriveService.get("test") match {

      case Some(repo) =>

        gdriveService.getFile()

    }

  }
  */

}
