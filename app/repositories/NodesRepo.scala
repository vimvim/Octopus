package repositories

import models.Node

/**
 *
 */
trait NodesRepo[T <: Node] {

  def save(entity: T): Unit

  def find(id: Int): Option[T]
}
