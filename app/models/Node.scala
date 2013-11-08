package models

import javax.persistence._
import scala.beans.BeanProperty

/**
 *
 */
@Entity
@Table(name="node")
@Inheritance(strategy=InheritanceType.JOINED)
class Node {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @BeanProperty
  @Column(name="id")
  var id: Int = _

}
