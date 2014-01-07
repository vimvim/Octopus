package octorise.repo.octopus.services

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.{Propagation, Transactional}
import org.springframework.beans.factory.annotation.{Qualifier, Autowired}

import octorise.repo.octopus.repositories.{StructuredContentRepo, NodesRepo, ContentRepo}
import octorise.repo.octopus.models.{StructuredContent, Content}

/**
 *
 */
@Service("structuredContentService")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
class StructuredContentServiceImpl extends ContentServiceImpl[StructuredContent] with StructuredContentService {

  @Autowired
  @Qualifier("structuredContentRepo")
  var _repo: StructuredContentRepo = _

  def repo(): NodesRepo[StructuredContent] = _repo

  protected def createEntity(): StructuredContent = new StructuredContent()
}
