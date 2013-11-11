package schema

import models.AttributeInt

/**
 *
 */
class AttributeDescriptorInt  extends AttributeDescriptor[Int] {

  def createAttribute(value: Int) = {
    new AttributeInt(value)
  }

}
