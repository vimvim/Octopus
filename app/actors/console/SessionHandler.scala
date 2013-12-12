package actors.console

import akka.actor.Actor

import play.api.Logger
import play.api.libs.iteratee.Concurrent.Channel
import play.api.libs.json.JsValue

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

      log.debug(s"Command received for user $userId session")






  }


}

