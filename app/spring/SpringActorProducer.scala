package spring

import akka.actor.{Actor, IndirectActorProducer}

import scala.Predef._
import scala.collection.mutable.ListBuffer

/**
 * Akka Spring integration for AKKA 2.2
 */
class SpringActorProducer(val actorClass: Class[_ <: Actor], val beanName: String, val beanArguments:ListBuffer[_] = null) extends IndirectActorProducer {

  // val actorClass: Class[_ <: Actor] = cls

  // val beanName: String = _beanName

  def produce(): Actor = {

    if (beanName.nonEmpty) {

      if (beanArguments!=null) {
        SpringContextHolder.getContext.getBean(beanName, beanArguments).asInstanceOf[Actor]
      } else {
        SpringContextHolder.getContext.getBean(beanName).asInstanceOf[Actor]
      }

    } else {

      SpringContextHolder.getContext.getBean(actorClass)
    }
  }

  //def actorClass: Class[_ <: Actor] = ???
}
