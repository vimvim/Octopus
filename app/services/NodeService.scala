package services

import models.Node

/**
 *
 */
trait NodeService[ T <: Node] {

  def create():Node

}
