package console

/**
 * Result of the command execution.
 *
 */
case class Result(label: String, output: String, delegatingCommands: List[DelegatedCommand]) { }
