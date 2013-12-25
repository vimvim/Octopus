package octorise.repo.octopus.schema

import octorise.repo.octopus.models.AttributeString

/**
 *
 */
class AttributeDescriptorString  extends AttributeDescriptor[String] {

  def createAttributeEntity() = new AttributeString()

}
