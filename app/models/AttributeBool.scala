package models

import javax.persistence.{Column, DiscriminatorValue, Entity}
import scala.beans.BeanProperty

/**
 *
 */
@Entity
@DiscriminatorValue("BOOL")
class AttributeBool(_value: Boolean) extends Attribute[Boolean] {

  @BeanProperty
  @Column(name="value_bool")
  var value: Boolean = _value

  def applyValue(value: Boolean) = this.value = value
}
