package actors.console

import java.io.{PrintWriter, PrintStream, ByteArrayOutputStream}

import akka.actor.Actor

import play.api.Logger
import play.api.libs.iteratee.Concurrent.Channel
import play.api.libs.json._
import play.api.libs.json.JsString
import play.api.libs.json.JsBoolean

import org.springframework.beans.factory.annotation.{Autowired, Qualifier, Configurable}

import org.codehaus.groovy.control.CompilerConfiguration
import groovy.lang._

import services.TweetService
import console.RootShell


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

  var shell:GroovyShell =_

  var outStream: ByteArrayOutputStream =_

  def receive: Actor.Receive = {

    case SessionCommand(jsValue) =>

      val command: JsValue = jsValue \ "command"
      val data: JsValue = jsValue \ "data"

      command match {

        case JsString("open") =>

          log.debug(s"Open session for user $userId")

          reportResult("Session opened")

        case JsString("execute") =>

          data match {

            case JsString(text) =>
              log.debug(s"Execute '$text' for user $userId session")

              try {

                shell.evaluate(text)
                val out = shell.getProperty("out").asInstanceOf[PrintWriter]
                out.flush()

                reportResult(outStream.toString)

              } catch {

                case e: MissingMethodException => reportError(e.getMessage)

                case e: MissingPropertyException => reportError(e.getMessage)

                case e: Exception => reportError(fullExceptionText(e))

              }

            case _ =>
              log.debug(s"Invalid command format received for user $userId session")

          }

        case JsString(cmd) =>

          log.debug(s"Unknown command $cmd received for user $userId session")

        case _ =>

          log.debug(s"Invalid command format received for user $userId session")

      }

  }

  override def preStart(): Unit = {

    log.debug(s"Start session handler for user $userId session")

    // TODO: Only for testing . Find better way to create GroovyShell ( ie: Spring, ... )

    outStream = new ByteArrayOutputStream()
    val printWriter = new PrintWriter(outStream)

    val bindings = new Binding()
    bindings.setProperty("out", printWriter)

    val configuration = new CompilerConfiguration()
    configuration.setScriptBaseClass(classOf[RootShell].getCanonicalName)

    shell = new GroovyShell(classOf[RootShell].getClassLoader, bindings, configuration)

    // val compilerConfiguration = new CompilerConfiguration();
    // compilerConfiguration.setScriptBaseClass(classOf[RootShell].getCanonicalName)

    // val scriptClassLoader = new GroovyClassLoader(classOf[RootShell].getClassLoader, compilerConfiguration)

    // shell = new GroovyShell(scriptClassLoader, new Binding(), compilerConfiguration)
  }

  override def postStop(): Unit = log.debug(s"Stop session handler for user $userId session")

  private def reportResult(message:String):Unit = {

    sender ! SimpleResponse(Json.obj(
      "success" -> JsBoolean(true),
      "response" -> JsString(message)
    ))
  }

  private def reportError(message:String):Unit = {

    log.debug(s"Something goes wrong for user $userId session: \r\n $message")

    sender ! SimpleResponse(Json.obj(
      "success" -> JsBoolean(false),
      "response" -> JsString(message)
    ))
  }

  private def fullExceptionText(e:Exception):String = {

    val stream = new ByteArrayOutputStream()
    val printStream = new PrintStream(stream)

    e.printStackTrace(printStream)

    printStream.toString
  }




}

