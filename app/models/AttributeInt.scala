package models

import javax.persistence.{Column, DiscriminatorValue, Entity}
import scala.beans.BeanProperty

/**
 *
 */
@Entity
@DiscriminatorValue("INT")
class AttributeInt extends Attribute[Int] {

  @BeanProperty
  @Column(name="value_int")
  var value: Int = _

  def applyValue(value: Int) = this.value = value

}
