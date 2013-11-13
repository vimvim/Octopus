package schema

import models.AttributeBool

/**
 *
 */
class AttributeDescriptorBool extends AttributeDescriptor[Boolean] {

  def createAttributeEntity() = new AttributeBool()

}
