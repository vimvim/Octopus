package models

import javax.persistence._
import scala.beans.BeanProperty


/**
 * Represent reference to the schema applied to the node.
 * The actual schema descriptor is not saved in the DB.
 */
@Entity
@Table(name="schema_refs")
class SchemaRef {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @BeanProperty
  @Column(name="id")
  var id: Int = _

  @BeanProperty
  @Column(name="name")
  var name: String = _

}
