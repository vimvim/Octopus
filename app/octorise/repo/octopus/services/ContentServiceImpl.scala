package octorise.repo.octopus.services

import org.springframework.stereotype.{Component, Service}
import org.springframework.transaction.annotation.{Propagation, Transactional}
import org.springframework.beans.factory.annotation.{Qualifier, Autowired}

import octorise.repo.octopus.models.{Node, Content}
import octorise.repo.octopus.repositories.{ContentRepo, NodesRepo}
import octorise.repo.octopus.schema.NodeType


@Component("nodeType.Content")
class ContentNodeType @Autowired()(

  @Autowired @Qualifier("nodeType.Node")
  baseType: NodeType[Node],

  @Autowired @Qualifier("contentService")
  service: ContentService,

  @Autowired @Qualifier("contentRepo")
  repo: ContentRepo

) extends NodeType[Content](classOf[Content], "content", baseType, service, repo)



@Service("contentService")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
class ContentServiceImpl extends AbstractNodeService[Content] with ContentService {

  @Autowired
  @Qualifier("contentRepo")
  var _repo: ContentRepo = _

  def repo(): NodesRepo[Content] = _repo

  protected def createEntity(): Content = new Content()
}
