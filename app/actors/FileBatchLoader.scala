package actors

import scala._

import java.io.{FileReader, BufferedReader}

import org.springframework.stereotype.Component
import org.springframework.context.annotation.Lazy

import akka.actor.{Props, ActorRef, Actor}
import akka.routing.RoundRobinRouter

import play.libs.Akka

import spring.SpringContextHolder


case class FileBatchLoad(val filePath: String, val workerBeanName:String)

case class LoadDataChunk(val line: String)

/**
 * Read file lines and pass it to the child actors for processing.
 */
@Component("FileBatchLoader.Actor")
@Lazy
class FileBatchLoader extends Actor {

  var workers: List[ActorRef] = _

  var router: ActorRef =_

  def receive: Actor.Receive = {

    case FileBatchLoad(filePath, workerBeanName) => {

      initWorkers(workerBeanName)

      val fileReader: BufferedReader = new BufferedReader(new FileReader(filePath))

      var line:String = null

      do {

        line = fileReader.readLine()
        if (line!=null) {
          router ! LoadDataChunk(line)
        }

      } while (line!= null)
    }
  }

  def initWorkers(workerBeanName:String) = {

    var workers: List[ActorRef] = List[ActorRef]()

    for(idx <- 0 to 5) {
      val actorRef = SpringContextHolder.getContext.getBean(workerBeanName).asInstanceOf[ActorRef]
      workers = workers :+ actorRef
    }

    router = Akka.system.actorOf(Props.empty.withRouter(RoundRobinRouter(routees = workers)))
  }
}
