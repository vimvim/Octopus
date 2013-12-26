package spring

import akka.actor.{Actor, IndirectActorProducer}

import scala.Predef._

/**
 * Akka Spring integration for AKKA 2.2
 * This is special AKKA Actor producer which is able to create Actor using Spring content.
 *
 * Calling flow:
 *    - Client call SpringAkka or request Actor described by SpringAkkaConfig configuration
 *    - SpringAkka call Akka system and specify custom AKKA producer ( SpringActorProducer )
 *    - Akka system call SpringActorProducer which in turns call Spring content and get Actor instance
 */
class SpringActorProducer(val actorClass: Class[_ <: Actor], val beanName: String, val beanArguments:java.util.List[_] = null) extends IndirectActorProducer {

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

      val appName = SpringContextHolder.getContext.getApplicationName
      val haveDefinition = SpringContextHolder.getContext.containsBeanDefinition("ConsoleSessionsManager.Actor")

      SpringContextHolder.getContext.getBean(actorClass)
    }
  }

  //def actorClass: Class[_ <: Actor] = ???
}
