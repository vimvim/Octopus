package console

import scala._
import groovy.lang.Script

/**

 */
abstract class RootShell extends Script {

  def info(): Unit = {
    println("Octopus Groovy console v.01")
  }



}
