package console

import scala._
import groovy.lang.{MissingMethodException, Script}

/**

 */
abstract class RootShell extends Script {

  /**
   * Print info about shell
   */
  def info(): Unit = {
    println("Octopus Groovy console v.01")
  }

  override def invokeMethod(name:String, args:Object):Object = {

    try {
      super.invokeMethod(name, args)
    } catch {
      case e:MissingMethodException => {

        val variable = binding.getVariable(name);

      }
      case e:Exception => throw e
    }

    // if the method was not found in the current scope (the script's methods)
    // let's try to see if there's a method closure with the same name in the binding
    /*
    catch (MissingMethodException mme) {
      try {
        if (name.equals(mme.getMethod())) {
          Object boundClosure = binding.getVariable(name);
          if (boundClosure != null && boundClosure instanceof Closure) {
            return ((Closure) boundClosure).call((Object[])args);
          } else {
            throw mme;
          }
        } else {
          throw mme;
        }
      } catch (MissingPropertyException mpe) {
        throw mme;
      }
    }
    */
  }

  /**
   * Open repository subshell
   * TODO: Needs to handle this using InvokeDynamic. Will check for command in the registered subshells.
   */
  // def repository(): Unit = {
  //
  // }

}
