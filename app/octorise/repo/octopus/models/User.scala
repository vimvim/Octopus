package octorise.repo.octopus.models

import javax.persistence._
import scala.reflect.BeanProperty


/**
 * Represent system user.
 *
 * @param _username
 */
@Entity
@Table(name = "users")
@PrimaryKeyJoinColumn(name = "id", referencedColumnName = "id")
class User(_username: String) extends Node {

  @BeanProperty
  @Column(name="username")
  var username: String = _username

  def this() = this (null)

  override def toString = id + " = " + username
}
