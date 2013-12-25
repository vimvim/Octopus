package octorise.console.shells

import octorise.console.DelegatedCommand

/**
 * Result of the command execution.
 *
 */
sealed class Result()

case class SuccessResult(label: String, output: String, delegatingCommands: List[DelegatedCommand]) extends Result

case class ErrorResult(label: String, output: String, error: String, delegatingCommands: List[DelegatedCommand]) extends Result
