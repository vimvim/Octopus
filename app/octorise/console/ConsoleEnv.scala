package octorise.console

import org.codehaus.groovy.runtime.InvokerHelper
import octorise.console.shells.{OctopusRepoShell, RootShell}

/**
 * Class initialize console ( groovy ) env at the startup.
 */
class ConsoleEnv {

  // Register metaclasses for each shell class. Metaclass will be responsible for managing and navigating to the subshells.

  InvokerHelper.metaRegistry.setMetaClass(classOf[RootShell],
    new ShellMetaClass(classOf[RootShell], Map[String, Class[_]](
      ("repo", classOf[OctopusRepoShell])
    ))
  )

  InvokerHelper.metaRegistry.setMetaClass(classOf[OctopusRepoShell], new ShellMetaClass(classOf[OctopusRepoShell], Map[String, Class[_]]()))
}
