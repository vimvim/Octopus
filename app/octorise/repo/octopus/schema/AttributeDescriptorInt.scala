package octorise.repo.octopus.schema

import octorise.repo.octopus.models.AttributeInt

/**
 *
 */
class AttributeDescriptorInt  extends AttributeDescriptor[Int] {

  def createAttributeEntity() = new AttributeInt()

}
