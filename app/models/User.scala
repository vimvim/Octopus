package models

import javax.persistence._
import scala.reflect.BeanProperty

@Entity
@Table(name = "user")
class User(_username: String) {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @BeanProperty
  var id: Int = _

  @BeanProperty
  var username: String = _username

  def this() = this (null)

  override def toString = id + " = " + username
}
