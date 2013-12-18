package console

import java.io.{PrintWriter, ByteArrayOutputStream}
import groovy.lang.{GroovyShell, Binding}
import org.codehaus.groovy.control.CompilerConfiguration


/**
 * Wrapper for groovy based console shell
 */
class ConsoleShell {

  var label = "octo #"

  var shellClasses:List[Class[_]] = List(classOf[console.RootShell])

  // var shellClass = classOf[RootShell]

  // var upperShellClasses = List[Class]

  val outStream = new ByteArrayOutputStream()

  val printWriter = new PrintWriter(outStream)

  val delegatedCommands = List[DelegatedCommand]()

  val bindings = new Binding()
  bindings.setProperty("out", printWriter)
  bindings.setProperty("delegatedCommands", delegatedCommands)

  var groovyShell:GroovyShell = null

  def execute(cmd:String):Result = {

    if (groovyShell==null) {

      val shellClass = shellClasses.head

      val configuration = new CompilerConfiguration()
      configuration.setScriptBaseClass(shellClass.getCanonicalName)

      groovyShell = new GroovyShell(shellClass.getClassLoader, bindings, configuration)
    }

    groovyShell.evaluate(cmd)

    val delegatedCommands = execDelegatedCommands(delegatedCommands)

    printWriter.flush()
    val output = outStream.toString

    outStream.reset()

    Result(label, output, delegatedCommands)
  }

  private def execDelegatedCommands(commands:List[DelegatedCommand]):List[DelegatedCommand] = {

    commands match {

      case command :: rest => {

        command match {

          case OpenSubShell(subShellClass) => {

            shellClasses = subShellClass::shellClasses
            groovyShell = null

            execDelegatedCommands(commands.tail)
          }

          case ReturnShell => {

            shellClasses = shellClasses.tail
            groovyShell = null

            execDelegatedCommands(commands.tail)
          }

          // Not known this delegated command - will pass the rest of list to the upper level for handling.
          case _ => commands
        }
      }

      case List() =>
        commands
    }
  }
}
