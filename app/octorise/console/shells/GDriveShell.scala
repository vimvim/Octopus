package octorise.console.shells

/**
 * Console subshell for managing gdrive located repos.
 */
abstract class GDriveShell extends BaseShell {

  /**
   * Print shell info
   */
  def info(): Unit = {
    println("GDrive repository management shell")
  }



}
