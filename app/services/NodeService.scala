package services

import models.Node
import repositories.NodesRepo

/**
 *
 */
trait NodeService[ T <: Node] {

  def create(initializer:(T) => Unit):T

  def repo(): NodesRepo[T]

}
