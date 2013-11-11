package schema

import models.AttributeBool

/**
 *
 */
class AttributeDescriptorBool extends AttributeDescriptor[Boolean] {

  def createAttribute(value: Boolean) = {
    new AttributeBool(value)
  }

}
