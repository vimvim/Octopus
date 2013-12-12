package controllers

import play.api.mvc.{Action, Controller}

/**
 * Content controller.
 * Responsible for rendering content.
 */
@org.springframework.stereotype.Controller
class Content extends Controller {

  def renderContent = Action {
    Ok(views.html.index("Application is ready."))
  }
}
