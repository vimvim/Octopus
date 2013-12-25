package octorise.repo.octopus.schema

import octorise.repo.octopus.models.{Node, AttributeNode}

/**
 *
 */
class AttributeDescriptorNode  extends AttributeDescriptor[Node] {

  def createAttributeEntity() = new AttributeNode()

}
