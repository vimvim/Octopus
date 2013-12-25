package octorise.repo.octopus.services

import octorise.repo.octopus.models.Node
import octorise.repo.octopus.repositories.NodesRepo

/**
 *
 */
trait NodeService[ T <: Node] {

  def create(initializer:(T) => Unit):T

  def update(initializer:(T) => Unit):T

  def repo(): NodesRepo[T]

}
