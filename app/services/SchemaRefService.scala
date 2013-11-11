package services

import models.SchemaRef
import schema.SchemaDescriptor

/**
 *
 */
trait SchemaRefService {

  def getRef(schema: SchemaDescriptor): SchemaRef
}
