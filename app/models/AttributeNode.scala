package models

import javax.persistence._
import scala.beans.BeanProperty

/**
 *
 */
@Entity
@DiscriminatorValue("NODE")
class AttributeNode extends Attribute[Node] {

  @BeanProperty
  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="value_node_id")
  var value: Node = _

  def applyValue(value: Node) = this.value = value
}
