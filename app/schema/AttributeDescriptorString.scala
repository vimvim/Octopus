package schema

import models.{AttributeString, AttributeNode, Node}

/**
 *
 */
class AttributeDescriptorString  extends AttributeDescriptor[String] {

  def createAttribute(value: String) = {
    new AttributeString(value)
  }

}
