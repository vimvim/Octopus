package console.shells

import scala._
import groovy.lang.{MissingMethodException, Script}
import console.shells.BaseShell

/**

 */
abstract class RootShell extends BaseShell {

  /**
   * Print info about shell
   */
  def info(): Unit = {
    println("Octopus Groovy console v.01")
  }

}
