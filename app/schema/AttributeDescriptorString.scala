package schema

import models.{AttributeString, AttributeNode, Node}

/**
 *
 */
class AttributeDescriptorString  extends AttributeDescriptor[String] {

  def createAttributeEntity() = new AttributeString()

}
