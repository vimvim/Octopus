package schema

import scala.beans.BeanProperty
import models.Attribute

/**
 *
 */
abstract class AttributeDescriptor[T] {

  @BeanProperty
  var schema: SchemaDescriptor = _

  @BeanProperty
  var name: String = _

  @BeanProperty
  var multiValue: Boolean = _

  def createAttribute(value: T): Attribute[T]

}
