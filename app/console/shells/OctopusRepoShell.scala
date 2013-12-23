package console.shells

import groovy.lang.GroovyCallable

/**
 * Shell allow to work with the nodes in the octopus repository.
 */
abstract class OctopusRepoShell extends BaseShell {

  /**
   * Print shell info
   */
  def info(): Unit = {
    println("Octopus repository editing shell")
  }

  /**
   * Open specified node
   *
   * @param slug    Node slug
   */
  def open(slug:String): Unit = {
    println("Will open node")
  }

  /**
   * Create node of the specified type.
   *
   * @param nodeType    Node type
   * @param initFunc    Initialize function
   */
  def create(nodeType:String, initFunc: GroovyCallable) = {

  }

}
