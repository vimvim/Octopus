package octorise.repo.octopus.services

import octorise.repo.octopus.models.SchemaRef
import octorise.repo.octopus.schema.SchemaDescriptor

/**
 *
 */
trait SchemaRefService {

  def getRef(schema: SchemaDescriptor): SchemaRef
}
