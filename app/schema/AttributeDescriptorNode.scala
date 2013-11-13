package schema

import models.{AttributeNode, Node}

/**
 *
 */
class AttributeDescriptorNode  extends AttributeDescriptor[Node] {

  def createAttributeEntity() = new AttributeNode()

}
