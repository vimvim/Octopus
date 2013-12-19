package console

import groovy.lang.Script

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

  /*
  override def invokeMethod(name:String, args:Object):Object = {

    try {
      super.invokeMethod(name, args)
    } catch {
      case e:MissingMethodException => {

        val variable = binding.getVariable(name);

      }
      case e:Exception => throw e
    }
  */

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
  // }

}
