package octorise.repo.octopus.repositories

import octorise.repo.octopus.models.SchemaRef

/**
 *
 */
trait SchemaRefsRepo {

  def save(schemaRef: SchemaRef): Unit

  def findByName(schemaName: String): Option[SchemaRef]

}
