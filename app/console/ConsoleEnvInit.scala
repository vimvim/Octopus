package console

import org.codehaus.groovy.runtime.InvokerHelper

/**
 * Class initialize console ( groovy ) env at the startup.
 */
class ConsoleEnvInit {

  this = {

    val shellMetaClass = new ShellMetaClass(String.class)
    InvokerHelper.metaRegistry.setMetaClass(String.class, myMetaClass)

  }

}
