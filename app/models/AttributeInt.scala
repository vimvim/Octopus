package models

import javax.persistence.{Column, DiscriminatorValue, Entity}
import scala.beans.BeanProperty
import org.hibernate.annotations.{Index, Table}
import scala.Array

/**
 *
 */
@Entity
@DiscriminatorValue("INT")
@Table(
  appliesTo = "attributes",
  indexes = Array(
    new Index(name="attr_by_value_int", columnNames=Array("schema_id", "name", "value_int"))
  )
)
class AttributeInt extends Attribute[Int] {

  @BeanProperty
  @Column(name="value_int")
  var value: Int = _

  def applyValue(value: Int) = this.value = value

}
