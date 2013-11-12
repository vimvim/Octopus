package models

import javax.persistence.{Column, DiscriminatorValue, Entity}
import scala.beans.BeanProperty

/**
 *
 */
@Entity
@DiscriminatorValue("STRING")
class AttributeString(_value: String) extends Attribute[String] {

  @BeanProperty
  @Column(name="value_string")
  var value: String = _value

  def applyValue(value: String) = this.value = value

}