package controllers

import play.api.mvc._
import play.api.mvc.Results._
import scala.Some
import models.User
import scala.Some
import play.api.mvc.SimpleResult

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
}

