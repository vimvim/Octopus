package console

/**
 * Shell implements common repositories management operations.
 */
abstract class RepositoriesShell extends BaseShell {

  def info(): Unit = {
    println("Repository management")
  }

  /**
   * Open specified repository. This will also lead to change current shell to the associated with the
   * opened repo.
   */
  def open(name:String): Unit = {
    println("Will open repository")
  }

}
