package schema

import org.springframework.stereotype.Service
import scala.beans.BeanProperty

/**
 *
 */

@Service("schemasRegistry")
class SchemasRegistry {

  var schemas: Map[String,SchemaDescriptor] = Map()

  def setSchemas(map: java.util.List[SchemaDescriptor]) = {

  }

  def getSchema(schemaName: String): SchemaDescriptor = {

    schemas.get(schemaName) match {
      case Some(schemaDescriptor) => schemaDescriptor
      case None => throw new Exception("Schema is not found:"+schemaName)
    }
  }
}
