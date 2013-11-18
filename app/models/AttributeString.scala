package models

import javax.persistence.{Column, DiscriminatorValue, Entity}
import scala.beans.BeanProperty
import org.hibernate.annotations.Table
import org.hibernate.annotations.Index

/**
 *
 */
@Entity
@DiscriminatorValue("STRING")
@Table(
  appliesTo = "attributes",
  indexes = Array(
    new Index(name="attr_by_value_str", columnNames=Array("schema_id", "name", "value_string"))
  )
)
class AttributeString extends Attribute[String] {

  @BeanProperty
  @Column(name="value_string")
  var value: String = _

  def applyValue(value: String) = this.value = value

}
