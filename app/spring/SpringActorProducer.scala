package spring

import akka.actor.{Actor, IndirectActorProducer}
import scala.Predef._

/**
 * Akka Spring integration for AKKA 2.2
 */
class SpringActorProducer(cls: Class[_ <: Actor], _beanName: String) extends IndirectActorProducer {

  val actorClass: Class[_ <: Actor] = cls

  val beanName: String = _beanName

  def produce(): Actor = {

    if (beanName.nonEmpty) {
      SpringContextHolder.getContext.getBean(beanName).asInstanceOf[Actor]
    } else {
      SpringContextHolder.getContext.getBean(actorClass)
    }
  }

  //def actorClass: Class[_ <: Actor] = ???
}
