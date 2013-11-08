package models

import javax.persistence.{GenerationType, GeneratedValue, Id}
import scala.beans.BeanProperty

/**
 *
 */
class Node {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @BeanProperty
  var id: Int = _

}
