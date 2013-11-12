package repositories

import models.SchemaRef

/**
 *
 */
trait SchemaRefsRepo {

  def save(schemaRef: SchemaRef): Unit

  def findByName(schemaName: String): Option[SchemaRef]

}
