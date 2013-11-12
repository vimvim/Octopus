package services

import models.{Node}
import repositories.{UsersRepo, NodesRepo}
import org.springframework.beans.factory.annotation.{Qualifier, Autowired}
import schema.SchemasRegistry

/**
 *
 */
abstract class AbstractNodeService[T <:Node] extends NodeService[T] {

  @Autowired
  @Qualifier("schemasRegistry")
  var schemasRegistry: SchemasRegistry = _

  @Autowired
  @Qualifier("schemaRefService")
  var schemaRefService: SchemaRefService = _

  def create(initializer:(T) => Unit): T = {

    val entity = createEntity()

    entity.beginEdit(schemasRegistry, schemaRefService)

    initializer(entity)

    entity.endEdit()

    repo().save(entity)

    entity
  }

  protected def createEntity(): T


}
