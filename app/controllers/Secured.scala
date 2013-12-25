package controllers

import scala.concurrent.Future
import scala.Some

import play.api.libs.iteratee.{Enumerator, Iteratee}
import play.api.libs.json.{Json, JsValue}
import play.api.libs.concurrent.Execution.Implicits._
import play.api.mvc._
import play.api.mvc.Results._
import play.api.mvc.SimpleResult

import octorise.repo.octopus.models.User


/**
 *
 */
trait Secured {

  def unauthorized(request: RequestHeader):SimpleResult = {
    Redirect("/login")
  }

  def user(request: RequestHeader):Option[User] = {

    request.session.get(Security.username) match {
      case Some(username) => Some(new User(username))
      case None => None
    }
  }

  def isAuthenticated(f: => User => Request[AnyContent] => SimpleResult) = {
    Security.Authenticated(user, unauthorized) { user =>
      Action(request => f(user)(request))
    }
  }

  /**
   * Authentication wrapper for web socket handler
   */
  def withAuthWS(f: => User => Future[(Iteratee[JsValue, Unit], Enumerator[JsValue])]): WebSocket[JsValue] = {

    def errorFuture = {
      // Just consume and ignore the input
      val in = Iteratee.ignore[JsValue]

      // Send a single 'Hello!' message and close
      val out = Enumerator(Json.toJson("not authorized")).andThen(Enumerator.eof)

      Future {
        (in, out)
      }
    }

    WebSocket.async[JsValue] {
      request =>
        user(request) match {
          case None => errorFuture
          case Some(user) => f(user)
        }
    }
  }
}

