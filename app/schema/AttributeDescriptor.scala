package schema

import scala.beans.BeanProperty
import models.Attribute

/**
 *
 */
abstract class AttributeDescriptor[T] {

  @BeanProperty
  var name: String = _

  @BeanProperty
  var multiValue: Boolean = false

  def createAttribute(value: T): Attribute[T]

}
