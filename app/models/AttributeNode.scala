package models

import javax.persistence._
import scala.beans.BeanProperty

/**
 *
 */
@Entity
@DiscriminatorValue("NODE")
class AttributeNode(_value: Node) extends Attribute[Node] {

  @BeanProperty
  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="value_node_id")
  var value: Node = _value

  def applyValue(value: Node) = this.value = value
}
