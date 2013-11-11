package models

import javax.persistence.{Column, DiscriminatorValue, Entity}
import scala.beans.BeanProperty

/**
 *
 */
@Entity
@DiscriminatorValue("INT")
class AttributeInt(_value: Int) extends Attribute[Int] {

  @BeanProperty
  @Column(name="value_int")
  var value: Int = _value

  def applyValue(value: Int) = this.value = value

}
