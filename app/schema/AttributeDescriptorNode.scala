package schema

import models.{AttributeNode, Node}

/**
 *
 */
class AttributeDescriptorNode  extends AttributeDescriptor[Node] {

  def createAttribute(value: Node) = {
    new AttributeNode(value)
  }

}
