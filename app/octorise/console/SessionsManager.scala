package octorise.console

import scala.collection.JavaConversions._
import scala.collection.mutable.ListBuffer

import akka.actor.{Props, ActorRef, Actor}

import play.api.Logger
import play.api.libs.iteratee.{Concurrent, Enumerator}
import play.api.libs.json.{JsNull, JsValue}
import play.api.libs.iteratee.Concurrent.Channel
import play.libs.Akka

import org.springframework.stereotype.Component
import org.springframework.context.annotation.Lazy


/**
 * Manage sessions and session handlers.
 */
@Component("ConsoleSessionsManager.Actor")
@Lazy
class SessionsManager extends Actor {

  lazy val log = Logger("application." + this.getClass.getName)

  var sessions = Map[Int, ConsoleSession]()

  def receive: Actor.Receive = {

    case CreateSession(userId) =>

      log.debug(s"Start new console session for user $userId")

      val consoleSession: ConsoleSession = sessions.get(userId) getOrElse {

        log.debug(s"Create new session handler for $userId")

        val broadcast: (Enumerator[JsValue], Channel[JsValue]) = Concurrent.broadcast[JsValue]

        val sessionHandler = Akka.system.actorOf(Props(classOf[SessionHandler], userId, broadcast._2))

        // sessionHandler ! SessionCommand(JsNull)

        ConsoleSession(userId, broadcast._1, broadcast._2, sessionHandler)
      }

      sessions += (userId -> consoleSession)

      // return the enumerator related to the user channel,
      // this will be used for create the WebSocket
      sender ! consoleSession

    case SessionClosed(userId) =>

      log.debug(s"Close console session for user $userId")

      sessions.get(userId) match {

        case Some(session) =>


        case None =>

      }
  }

}
