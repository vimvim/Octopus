package octorise.repo.octopus.services

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.{Propagation, Transactional}
import org.springframework.beans.factory.annotation.{Qualifier, Autowired}

import octorise.repo.octopus.models.TextContent
import octorise.repo.octopus.repositories.{TextContentRepo, NodesRepo}

/**
 *
 */
@Service("textContentService")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
class TextContentServiceImpl extends ContentServiceImpl[TextContent] with TextContentService {

  @Autowired
  @Qualifier("textContentRepo")
  var _repo: TextContentRepo = _

  def repo(): NodesRepo[TextContent] = _repo

  protected def createEntity(): TextContent = new TextContent()

}
