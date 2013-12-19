package console

import org.codehaus.groovy.runtime.InvokerHelper

/**
 * Class initialize console ( groovy ) env at the startup.
 */
class ConsoleEnv {

  InvokerHelper.metaRegistry.setMetaClass(classOf[RootShell],
    new ShellMetaClass(classOf[RootShell], Map[String, Class[_]](
      ("repo", classOf[RepositoriesShell])
    ))
  )

  InvokerHelper.metaRegistry.setMetaClass(classOf[RepositoriesShell], new ShellMetaClass(classOf[RepositoriesShell], Map[String, Class[_]]()))
}
