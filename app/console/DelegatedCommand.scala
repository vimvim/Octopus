package console

/**
 * Represent command delegated for execution from console script to upper level class.
 */
sealed class DelegatedCommand() { }

/**
 * Open sub shell
 *
 * @param shellClass    Shell class
 */
case class OpenSubShell[T<: BaseShell](name: String, shellClass:Class[T]) extends DelegatedCommand

/**
 * Return from sub shell to the upper level shell.
 *
 */
case class ReturnShell() extends DelegatedCommand
