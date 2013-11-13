package models

import javax.persistence.{Column, DiscriminatorValue, Entity}
import scala.beans.BeanProperty

/**
 *
 */
@Entity
@DiscriminatorValue("BOOL")
class AttributeBool extends Attribute[Boolean] {

  @BeanProperty
  @Column(name="value_bool")
  var value: Boolean = _

  def applyValue(value: Boolean) = this.value = value
}
