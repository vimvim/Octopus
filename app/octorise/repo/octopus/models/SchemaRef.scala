package octorise.repo.octopus.models

import scala.beans.BeanProperty
import javax.persistence._


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
