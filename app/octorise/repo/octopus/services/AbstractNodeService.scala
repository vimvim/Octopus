package octorise.repo.octopus.services

import org.springframework.beans.factory.annotation.{Qualifier, Autowired}
import octorise.repo.octopus.models.Node
import octorise.repo.octopus.schema.SchemasRegistry
import org.springframework.transaction.interceptor.TransactionAspectSupport
import org.springframework.transaction.annotation.{Propagation, Transactional}

/**
 *
 */
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
abstract class AbstractNodeService[T <:Node] extends NodeService[T] {

  @Autowired
  @Qualifier("schemasRegistry")
  var schemasRegistry: SchemasRegistry = _

  @Autowired
  @Qualifier("schemaRefService")
  var schemaRefService: SchemaRefService = _

  def create(initializer:(T) => Unit): T = {

    // TODO: For debugging only
    val transactionStatus = TransactionAspectSupport.currentTransactionStatus()

    val entity = createEntity()

    entity.beginEdit(schemasRegistry, schemaRefService)

    initializer(entity)

    entity.endEdit()

    repo().save(entity)

    entity
  }

  def update(node:T, updater:(T) => Unit):T = {

    // TODO: For debugging only
    // val transactionStatus = TransactionAspectSupport.currentTransactionStatus()

    node.beginEdit(schemasRegistry, schemaRefService)

    updater(node)

    node.endEdit()

    repo().save(node)

    node
  }

  def delete(node:T):Unit = {

    repo().delete(node)

  }

  protected def createEntity(): T


}
