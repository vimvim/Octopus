package octorise.console

import java.io.{PrintStream, PrintWriter, ByteArrayOutputStream}

import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.runtime.InvokerHelper
import groovy.lang.{MissingPropertyException, MissingMethodException, GroovyShell, Binding}

import octorise.console.shells._
import octorise.repo.octopus.models.Node
import octorise.console.shells.SuccessResult
import octorise.console.shells.ErrorResult


/**
 * Wrapper for groovy based console shell
 */
class ConsoleShell {

  // var shellClasses:List[Class[_]] = List(classOf[RootShell])
  var shellRecords:List[ShellRecord[_]] = List(ShellRecord("octo", classOf[RootShell]))

  // var subShellLabels = List[String]("octo")

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

    val shellRecord = shellRecords.head
    val shellClass = shellRecord.shellClass
    val shellMetaClass = InvokerHelper.metaRegistry.getMetaClass(shellClass)

    if (groovyShell==null) {

      val configuration = new CompilerConfiguration()
      configuration.setScriptBaseClass(shellClass.getCanonicalName)

      groovyShell = new GroovyShell(shellClass.getClassLoader, bindings, configuration)

      shellRecord.onOpenShell()
    }

    try {

      val script = groovyShell.parse(cmd)
      script.setBinding(bindings)
      script.setMetaClass(shellMetaClass)

      script.run()

      // groovyShell.evaluate(cmd)

      // Unhandled delegated commands will be passed to the upper level for handling
      val commandsTail = execDelegatedCommands(this.delegatedCommands)

      new SuccessResult(prompt, output, commandsTail)

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
  def prompt:String = shellRecords.reverse.foldLeft("")((prompt,record) => {
    val label = record.label
    s"$prompt/$label"
  })


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

          case OpenObjectShell(name, obj) =>

            // TODO: Matching object class -> shell class must be moved to the separate object
            obj match {
              case node:Node =>

                def getCurrentNode = {

                  try {
                    bindings.getProperty("node")
                  } catch {
                    case e: MissingPropertyException => null
                  }
                }

                openSubShell(name, classOf[OctopusRepoShell],
                  onOpenShell = ()=>{
                    bindings.setProperty("node", node)
                  },
                  onCloseShell = ()=>{
                    bindings.setProperty("node", getCurrentNode)
                  }
                )
            }

            execDelegatedCommands(commands.tail)

          case OpenSubShell(name, subShellClass) =>

            openSubShell(name, subShellClass)

            execDelegatedCommands(commands.tail)

          case ReturnShell() =>

            val shellRecord = shellRecords.head
            shellRecord.onCloseShell()

            shellRecords = shellRecords.tail

            groovyShell = null

            execDelegatedCommands(commands.tail)

          // Not known this delegated command - will pass the rest of list to the upper level for handling.
          case _ => stopProcessing(commands)
        }
      }

      case List() => stopProcessing(commands)
    }
  }

  private def openSubShell[T <: BaseShell](name:String, subShellClass:Class[T], onOpenShell:()=>Unit = ()=>{}, onCloseShell:()=>Unit = ()=>{}) = {

    shellRecords = ShellRecord(name, subShellClass, onOpenShell, onCloseShell)::shellRecords

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
   * @param e   Exception
   * @return
   */
  private def fullExceptionText(e:Exception):String = {

    val stream = new ByteArrayOutputStream()
    val printStream = new PrintStream(stream)

    e.printStackTrace(printStream)

    printStream.toString
  }
}
