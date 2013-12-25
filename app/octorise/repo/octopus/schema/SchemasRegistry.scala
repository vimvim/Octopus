package octorise.repo.octopus.schema

import org.springframework.stereotype.Service
import scala.beans.BeanProperty

/**
 *
 */

@Service("schemasRegistry")
class SchemasRegistry {

  var schemas: Map[String,SchemaDescriptor] = Map()

  def setSchemas(schemasDescriptors: java.util.List[SchemaDescriptor]) = {

    val itr = schemasDescriptors.iterator()

    while (itr.hasNext) {

      val schemaDescriptor = itr.next()

      schemas = schemas + Pair(schemaDescriptor.getName, schemaDescriptor)
    }
  }

  def getSchema(schemaName: String): SchemaDescriptor = {

    schemas.get(schemaName) match {
      case Some(schemaDescriptor) => schemaDescriptor
      case None => throw new Exception("Schema is not found:"+schemaName)
    }
  }
}
