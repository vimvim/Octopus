package models

import javax.persistence._
import scala.reflect.BeanProperty

@Entity
@Table(name = "user")
class User(_username: String) extends Node {

  @BeanProperty
  var username: String = _username

  def this() = this (null)

  override def toString = id + " = " + username
}
