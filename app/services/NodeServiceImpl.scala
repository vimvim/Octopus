package services

import models.Node
import org.springframework.stereotype.Service

/**
 *
 */
@Service("nodeService")
class NodeServiceImpl extends AbstractNodeService[Node] {

  def create():Node = {
    throw new NotImplementedError("Create method is not implemented for general node type")
  }

}
