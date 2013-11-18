package models

import javax.persistence._
import scala.beans.BeanProperty
import org.hibernate.annotations.Index
import javax.persistence.Table
import scala.Array

/**
 *
 */
@Entity
@Table(name="attributes")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="attr_type", discriminatorType=DiscriminatorType.STRING)
@org.hibernate.annotations.Table(
  appliesTo = "attributes",
  indexes = Array(
    new Index(name="attr_by_name1", columnNames=Array("node_id","schema_id", "name")),
    new Index(name="attr_by_name2", columnNames=Array("schema_id", "name"))
  )
)
abstract class Attribute[T] {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @BeanProperty
  @Column(name="id")
  var id: Int = _

  @BeanProperty
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
