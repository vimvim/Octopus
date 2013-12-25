package octorise.console.shells

import scala._

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
