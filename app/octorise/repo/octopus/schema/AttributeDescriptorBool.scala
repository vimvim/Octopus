package octorise.repo.octopus.schema

import octorise.repo.octopus.models.AttributeBool

/**
 *
 */
class AttributeDescriptorBool extends AttributeDescriptor[Boolean] {

  def createAttributeEntity() = new AttributeBool()

}
