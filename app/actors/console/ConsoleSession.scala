package actors.console


import play.api.libs.iteratee.Enumerator
import play.api.libs.json.JsValue
import play.api.libs.iteratee.Concurrent.Channel

import akka.actor.ActorRef

/**
 * Represent console session.
 */
case class ConsoleSession(userId:Int, enumerator:Enumerator[JsValue], channel:Channel[JsValue], handler:ActorRef)
