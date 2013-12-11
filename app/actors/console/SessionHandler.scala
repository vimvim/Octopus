package actors.console

import akka.actor.Actor

import play.api.Logger
import play.api.libs.iteratee.Concurrent.Channel
import play.api.libs.json.JsValue

import org.springframework.stereotype.Component
import org.springframework.context.annotation.{Scope, Lazy}

/**
 * Handle console session
 */
@Component("ConsoleSessionHandler.Actor")
@Scope("prototype")
@Lazy
class SessionHandler(val userId:Int, val channel:Channel[JsValue]) extends Actor {

  lazy val log = Logger("application." + this.getClass.getName)

  def receive: Actor.Receive = {

    case SessionCommand(jsValue) =>

      log.debug(s"Command received for user $userId session")






  }


}

object SessionHandlerFactory {
  def create(userId:Int, channel:Channel[JsValue]) = new SessionHandler(userId, channel)
}