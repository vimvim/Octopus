package actors.console

import akka.actor.Actor

import play.api.Logger
import play.api.libs.iteratee.Concurrent.Channel
import play.api.libs.json._

import org.springframework.beans.factory.annotation.{Autowired, Qualifier, Configurable}

import services.TweetService


/**
 * Handle console session
 */
@Configurable
class SessionHandler(val userId:Int, val channel:Channel[JsValue]) extends Actor {

  lazy val log = Logger("application." + this.getClass.getName)

  // ONLY FOR TESTING
  @Autowired
  @Qualifier("tweetService")
  var tweetService: TweetService =_

  def receive: Actor.Receive = {

    case SessionCommand(jsValue) =>

      val command: JsValue = jsValue \ "command"
      val data: JsValue = jsValue \ "data"

      command match {

        case JsString("open") =>

          log.debug(s"Open session for user $userId")

          sender ! SimpleResponse(Json.obj(
            "success" -> JsBoolean(true),
            "response" -> JsString("Session opened")
          ))

        case JsString("execute") =>

          data match {

            case JsString(text) =>
              log.debug(s"Execute '$text' for user $userId session")

            case _ =>
              log.debug(s"Invalid command format received for user $userId session")

          }

        case JsString(cmd) =>

          log.debug(s"Unknown command $cmd received for user $userId session")

        case _ =>

          log.debug(s"Invalid command format received for user $userId session")

      }

  }


}

