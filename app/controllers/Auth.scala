package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data._
import play.api.data.Forms._
import play.api.mvc.Results._

import format.Formats._

import models.User

case class AuthData(username: String, password:String)

/**
 * Handle user login
 */
class Auth extends Controller {

  lazy val loginBackUrl: String = "loginBack"

  val authForm = Form(
    mapping(
      "username" -> text,
      "password" -> text
    )(AuthData.apply)(AuthData.unapply)
  )

  /**
   * Display login page
   *
   * @return
   */
  def login = Action {
    request => {

      var backUrl = "/"

      request.getQueryString(loginBackUrl) match {
        case Some(url) => backUrl=url
        case None =>
      }

      Ok(views.html.login("")).withSession(loginBackUrl -> backUrl)
    }
  }

  /**
   * Handle login data
   *
   * @return
   */
  def auth = Action { implicit request=>

    val authData: AuthData = authForm.bindFromRequest().get

    val user = new User(authData.username)

    request.session.get(loginBackUrl) match {
      case Some(url) => handleSuccessLogin(user, url)
      case None => handleSuccessLogin(user, "/")
    }
  }

  def handleSuccessLogin(user:User, redirectUrl: String) = Redirect(redirectUrl).withSession(Security.username -> user.username)

}
