package console

/**
 * Represent command delegated for execution from console script to upper level class.
 */
sealed case class DelegatedCommand() { }

/**
 * Open sub shell
 *
 * @param shellClass    Shell class
 */
case class OpenSubShell(shellClass:Class) extends DelegatedCommand

/**
 * Return from sub shell to the upper level shell.
 *
 */
case class ReturnShell() extends DelegatedCommand
