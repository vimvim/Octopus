package schema

import models.AttributeInt

/**
 *
 */
class AttributeDescriptorInt  extends AttributeDescriptor[Int] {

  def createAttributeEntity() = new AttributeInt()

}
