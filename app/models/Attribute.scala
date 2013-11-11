package models

import javax.persistence._
import scala.beans.BeanProperty

/**
 *
 */
@Entity
@Table(name="attributes")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="attr_type", discriminatorType=DiscriminatorType.STRING)
abstract class Attribute[T] {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @BeanProperty
  @Column(name="id")
  var id: Int = _

  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="node_id")
  var node: Node =_

  @BeanProperty
  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="schema_id")
  var schemaRef: SchemaRef = _

  @BeanProperty
  @Column(name="name")
  var name: String = _

  def applyValue(value: T)
}
