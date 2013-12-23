package console.shells

import groovy.lang.Script
import console.{DelegatedCommand, ReturnShell}

/**
 *
 */
abstract class BaseShell extends Script {

  def ret():Unit = {
    delegateCommand(new ReturnShell)
  }

  def close():Unit = {
    delegateCommand(new ReturnShell)
  }

  private def delegateCommand(cmd:DelegatedCommand) {

    val binding = getBinding

    var delegatedCommands = binding.getProperty("delegatedCommands").asInstanceOf[List[DelegatedCommand]]
    delegatedCommands = delegatedCommands :+ cmd

    binding.setProperty("delegatedCommands", delegatedCommands)
  }
}
