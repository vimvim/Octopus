package models

import javax.persistence._
import scala.reflect.BeanProperty

/**
 * Represent system user.
 *
 * @param _username
 */
@Entity
@Table(name = "user")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
class User(_username: String) extends Node {

  @BeanProperty
  var username: String = _username

  def this() = this (null)

  override def toString = id + " = " + username
}
