package console

import scala._
import groovy.lang.Script

/**

 */
abstract class RootShell extends Script {

  /**
   * Print info about shell
   */
  def info(): Unit = {
    println("Octopus Groovy console v.01")
  }

  /**
   * Open repository subshell
   * TODO: Needs to handle this using InvokeDynamic. Will check for command in the registered subshells.
   */
  def repository(): Unit = {

  }

}
