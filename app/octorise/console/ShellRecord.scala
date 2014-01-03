package octorise.console

import octorise.console.shells.BaseShell

/**
 *
 */
case class ShellRecord[T <: BaseShell](label:String, shellClass:Class[T], onOpenShell:()=>Unit = ()=>{}, onCloseShell:()=>Unit = ()=>{})
