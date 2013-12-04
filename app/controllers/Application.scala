package controllers

import play.api._
import play.api.mvc._

@org.springframework.stereotype.Controller
class Application extends Controller {

  def index = Action {
    Ok(views.html.index("Application is ready."))
  }

}