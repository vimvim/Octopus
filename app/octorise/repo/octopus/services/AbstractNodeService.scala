package octorise.repo.octopus.services

import org.springframework.beans.factory.annotation.{Qualifier, Autowired}
import octorise.repo.octopus.models.Node
import octorise.repo.octopus.schema.SchemasRegistry

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

  def update(node:T, updater:(T) => Unit):T = {

    node.beginEdit(schemasRegistry, schemaRefService)

    updater(node)

    node.endEdit()

    repo().save(node)

    node
  }

  protected def createEntity(): T


}
