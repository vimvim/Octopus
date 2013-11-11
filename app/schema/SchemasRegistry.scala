package schema

/**
 *
 */
class SchemasRegistry {

  var schemas: Map[String,SchemaDescriptor] =_

  def getSchema(schemaName: String): SchemaDescriptor = {

    schemas.get(schemaName) match {
      case Some(schemaDescriptor) => schemaDescriptor
      case None => throw new Exception("Schema is not found:"+schemaName)
    }
  }



}
