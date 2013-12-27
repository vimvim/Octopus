package octorise.console

import octorise.console.shells.BaseShell

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
 * OPen shell associated with the specified object.
 *
 * @param name            Shell name
 * @param objectClass     Object class ( will be used for determine shell class )
 */
case class OpenObjectShell(name: String, objectClass:Class[_]) extends DelegatedCommand

/**
 * Return from sub shell to the upper level shell.
 *
 */
case class ReturnShell() extends DelegatedCommand
