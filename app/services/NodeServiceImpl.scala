package services

import models.Node

/**
 *
 */
class NodeServiceImpl extends AbstractNodeService[Node] {

  def create():Node = {
    throw new NotImplementedError("Create method is not implemented for general node type")
  }

}
