package octorise.console

import java.io.{PrintWriter, PrintStream, ByteArrayOutputStream}

import akka.actor.Actor

import play.api.Logger
import play.api.libs.iteratee.Concurrent.Channel
import play.api.libs.json._
import play.api.libs.json.JsString
import play.api.libs.json.JsBoolean

import org.springframework.beans.factory.annotation.{Autowired, Qualifier, Configurable}


import octorise.console.shells.{ErrorResult, SuccessResult}


/**
 * Handle console session
 */
@Configurable
class SessionHandler(val userId:Int, val channel:Channel[JsValue]) extends Actor {

  lazy val log = Logger("application." + this.getClass.getName)

  // ONLY FOR TESTING
  // @Autowired
  // @Qualifier("tweetService")
  // var tweetService: TweetService =_

  val consoleShell = new ConsoleShell()

  def receive: Actor.Receive = {

    case SessionCommand(jsValue) =>

      val command: JsValue = jsValue \ "command"
      val data: JsValue = jsValue \ "data"

      command match {

        case JsString("open") =>

          log.debug(s"Open session for user $userId")

          reportResult(consoleShell.prompt, "Session opened")

        case JsString("execute") =>

          data match {

            case JsString(text) =>

              log.debug(s"Execute '$text' for user $userId session")

              consoleShell.execute(text) match {
                case SuccessResult(prompt, output, delegatingCommands) =>  reportResult(prompt, output)
                case ErrorResult(prompt, output, error, delegatingCommands) => reportError(prompt, output, error)
              }

            case _ => log.debug(s"Invalid command format received for user $userId session")

          }

        case JsString(cmd) => log.debug(s"Unknown command $cmd received for user $userId session")

        case _ => log.debug(s"Invalid command format received for user $userId session")

      }
  }

  override def preStart(): Unit = log.debug(s"Start session handler for user $userId session")

  override def postStop(): Unit = log.debug(s"Stop session handler for user $userId session")

  private def reportResult(prompt:String, output:String):Unit = {

    log.debug(s"Command completed for user $userId session: $prompt # $output \r\n ")

    sender ! SimpleResponse(Json.obj(
      "success" -> JsBoolean(value = true),
      "prompt" -> JsString(prompt),
      "output" -> JsString(output)
    ))
  }

  private def reportError(prompt:String, output:String, error:String):Unit = {

    log.debug(s"Something goes wrong for user $userId session: \r\n $error")

    sender ! SimpleResponse(Json.obj(
      "success" -> JsBoolean(value = false),
      "prompt" -> JsString(prompt),
      "output" -> JsString(output),
      "error" -> JsString(error)
    ))
  }

}

