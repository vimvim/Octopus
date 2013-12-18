package console

import groovy.lang.Script

/**
 * Shell implements common repositories management operations.
 */
abstract class RepositoriesShell extends Script {

  /**
   * Open specified repository. This will also lead to change current shell to the associated with the
   * opened repo.
   */
  def open(name:String): Unit = {
    println("Octopus Groovy console v.01")
  }


}
