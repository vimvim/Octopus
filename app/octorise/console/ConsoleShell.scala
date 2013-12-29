package octorise.console

import java.io.{PrintStream, PrintWriter, ByteArrayOutputStream}

import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.runtime.InvokerHelper
import groovy.lang.{MissingPropertyException, MissingMethodException, GroovyShell, Binding}

import octorise.console.shells._
import octorise.repo.octopus.models.Node
import octorise.console.shells.SuccessResult
import octorise.console.OpenSubShell
import octorise.console.OpenObjectShell
import octorise.console.shells.ErrorResult
import octorise.console.ReturnShell


/**
 * Wrapper for groovy based console shell
 */
class ConsoleShell {

  var shellClasses:List[Class[_]] = List(classOf[RootShell])

  var subShellLabels = List[String]("Octo")

  val outStream = new ByteArrayOutputStream()

  val printWriter = new PrintWriter(outStream)

  val bindings = new Binding()
  bindings.setProperty("out", printWriter)
  bindings.setProperty("delegatedCommands", List[DelegatedCommand]())

  var groovyShell:GroovyShell = null

  /**
   * Execute command ( or small script )
   *
   * @param cmd     Script text
   * @return
   */
  def execute(cmd:String):Result = {

    val shellClass = shellClasses.head
    val shellMetaClass = InvokerHelper.metaRegistry.getMetaClass(shellClass)

    if (groovyShell==null) {

      val configuration = new CompilerConfiguration()
      configuration.setScriptBaseClass(shellClass.getCanonicalName)

      groovyShell = new GroovyShell(shellClass.getClassLoader, bindings, configuration)
    }

    try {

      val script = groovyShell.parse(cmd)
      script.setBinding(bindings)
      script.setMetaClass(shellMetaClass)

      script.run()

      // groovyShell.evaluate(cmd)

      new SuccessResult(prompt, output, execDelegatedCommands(this.delegatedCommands))

    } catch {

      case e: MissingMethodException => handleError(e.getMessage)

      case e: MissingPropertyException => handleError(e.getMessage)

      case e: Exception => handleError(fullExceptionText(e))

    }
  }

  /**
   * Get shell prompt
   *
   * @return
   */
  def prompt:String = subShellLabels.reverse.foldLeft("")((prompt,name) => s"$prompt/$name")

  /**
   * Handle script runtime error.
   *
   * @param errorText   Error text
   * @return
   */
  private def handleError(errorText:String):Result = {
    new ErrorResult(prompt, output, errorText, execDelegatedCommands(this.delegatedCommands))
  }

  /**
   * Execute commands delegated by script. Stop execution on the unknown command and return the rest of the list.
   *
   * @param commands    Commands delegated by script
   * @return
   */
  private def execDelegatedCommands(commands:List[DelegatedCommand]):List[DelegatedCommand] = {

    // Will call when we stop processing commands list
    def stopProcessing(commands:List[DelegatedCommand]) = {
      bindings.setProperty("delegatedCommands", commands)
      commands
    }

    commands match {

      case command :: rest => {

        command match {

          case OpenObjectShell(name, objectClass) =>

            subShellLabels = name::subShellLabels
            objectClass match {
              case node:Node => openSubShell(name, classOf[OctopusRepoShell])
            }

            execDelegatedCommands(commands.tail)

          case OpenSubShell(name, subShellClass) =>

            openSubShell(name, subShellClass)

            execDelegatedCommands(commands.tail)

          case ReturnShell() =>

            subShellLabels = subShellLabels.tail
            shellClasses = shellClasses.tail

            groovyShell = null

            execDelegatedCommands(commands.tail)

          // Not known this delegated command - will pass the rest of list to the upper level for handling.
          case _ => stopProcessing(commands)
        }
      }

      case List() => stopProcessing(commands)
    }
  }

  private def openSubShell[T <: BaseShell](name:String, subShellClass:Class[T]) = {

    subShellLabels = name::subShellLabels
    shellClasses = subShellClass::shellClasses

    groovyShell = null
  }

  /**
   * Get list of the delegated commands.
   *
   * @return
   */
  private def delegatedCommands:List[DelegatedCommand] = bindings.getProperty("delegatedCommands").asInstanceOf[List[DelegatedCommand]]

  /**
   * Return newly generated script output. Script output will be flushed after it is returned.
   *
   * @return
   */

  private def output:String = {

    printWriter.flush()
    val output = outStream.toString

    outStream.reset()

    output
  }

  /**
   * Helper for converting stack trace to the string
   *
   * @param e
   * @return
   */
  private def fullExceptionText(e:Exception):String = {

    val stream = new ByteArrayOutputStream()
    val printStream = new PrintStream(stream)

    e.printStackTrace(printStream)

    printStream.toString
  }
}
