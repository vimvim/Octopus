package actors.console

import play.api.libs.json.JsValue


sealed trait SessionMessage {

}

case class CreateSession(userId: Int) extends SessionMessage

case class SessionClosed(userId: Int) extends SessionMessage

case class SessionCommand(userId: Int, jsValue:JsValue) extends SessionMessage

