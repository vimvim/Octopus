package controllers

import scala.concurrent.Future
import scala.concurrent.duration._

import akka.actor.{ActorRef, Props}
import akka.pattern.ask
import akka.util.Timeout

import play.api.mvc._
import play.api.mvc.Results._
import play.api.libs.json._
import play.api.libs.concurrent._
import play.api.libs.iteratee._
import play.api.Play.current
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.iteratee.{Enumerator, Iteratee}
import play.api.Routes

import org.springframework.beans.factory.annotation.{Qualifier, Autowired}

import octorise.repo.octopus.models.User
import octorise.console._
import octorise.console.ConsoleSession
import octorise.console.CreateSession
import octorise.console.SessionCommand
import octorise.console.SessionClosed

/**
 *
 */
@org.springframework.stereotype.Controller
class Console extends Controller with Secured {

  @Autowired
  @Qualifier("consoleSessionManager")
  var sessionManager:ActorRef =_

  /**
   * Will render console page or login page depends on the session auth info
   *
   * @return
   */
  def index = isAuthenticated { user => implicit request =>
    Ok(views.html.console(user.username))
  }

  /**
   * WebSocket handler
   *
   * @return
   */

  def ws = withAuthWS { user =>

    implicit val timeout = Timeout(3 seconds)

    (sessionManager ? CreateSession(user.getId)).
      asInstanceOf[Future[ConsoleSession]] map {

      session =>

        (Iteratee.foreach[JsValue] {
          jsValue=>
            (session.handler ? SessionCommand(jsValue)) onSuccess {

              case SimpleResponse(respJsValue)   =>
                session.channel.push(respJsValue)

              case _ => println("Unexpected response")
            }

        } mapDone {
          _ =>
            sessionManager ! SessionClosed(user.getId)

        }, session.enumerator)
    }
  }

  /*
  def ws = WebSocket.using[String] {
    request =>

    //Concurernt.broadcast returns (Enumerator, Concurrent.Channel)
      val (out, channel) = Concurrent.broadcast[String]

      //log the message to stdout and send response back to client
      val in = Iteratee.foreach[String] {
        msg => println(msg)
          //the Enumerator returned by Concurrent.broadcast subscribes to the channel and will
          //receive the pushed messages
          channel push ("RESPONSE: " + msg)
      }
      (in, out)
  }
  */

}


