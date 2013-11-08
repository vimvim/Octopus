package services

import models.{Node}
import repositories.NodesRepo

/**
 *
 */
abstract class AbstractNodeService[T <:Node] extends NodeService[T] {

  def create(initializer:(T) => Unit): T = {

    val entity = createEntity()
    initializer(entity)

    repo().save(entity)

    entity
  }

  protected def createEntity(): T


}
