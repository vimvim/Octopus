package console

import groovy.lang.{MetaProperty, DelegatingMetaClass}
import console.shells.BaseShell


/**
 * Common meta class for shell classes
 */
class ShellMetaClass[T <: BaseShell](cls:Class[T], subShells:Map[String, Class[_]]) extends DelegatingMetaClass(cls) {

  initialize()

  override def hasProperty(obj: AnyRef, name: String): MetaProperty = {
    super.hasProperty(obj, name)
  }

  override def getProperty(obj: AnyRef, property: String): AnyRef = {

    subShells.get(property) match {

      case Some(subShellClass) => {

        // TODO: Needs to instantiate and return shell
        null
      }

      case None => super.getProperty(obj, property)
    }
  }

  override def invokeMethod(obj: AnyRef, methodName: String, arguments: AnyRef): AnyRef = {

    subShells.get(methodName) match {

      case Some(subShellClass) => {

        requestSubShell(obj, methodName, subShellClass.asInstanceOf[Class[BaseShell]])
        null
      }

      case None => super.invokeMethod(obj, methodName, arguments)
    }
  }

  override def invokeMethod(obj: AnyRef, methodName: String, arguments: Array[AnyRef]): AnyRef = {
    super.invokeMethod(obj, methodName, arguments)
  }

  private def requestSubShell[SH <: BaseShell](obj: AnyRef, name: String, subShellClass:Class[SH]) = {

    var delegatedCommands = obj.asInstanceOf[BaseShell].getBinding.getProperty("delegatedCommands").asInstanceOf[List[DelegatedCommand]]
    delegatedCommands = delegatedCommands :+ new OpenSubShell(name, subShellClass)

    obj.asInstanceOf[BaseShell].getBinding.setProperty("delegatedCommands", delegatedCommands)
  }

}
