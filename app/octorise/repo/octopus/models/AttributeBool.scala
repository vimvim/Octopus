package octorise.repo.octopus.models

import scala.beans.BeanProperty
import javax.persistence.{Column, DiscriminatorValue, Entity}


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
