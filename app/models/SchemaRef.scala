package models

import javax.persistence._
import scala.beans.BeanProperty

/**
 * Represent reference to the schema applied to the node.
 * The actual schema descriptor is not saved in the DB.
 */
@Entity
@Table(name="schema_refs")
@Inheritance(strategy=InheritanceType.JOINED)
class SchemaRef {

  @Id
  @BeanProperty
  @Column(name="name")
  var name: Int = _

}
