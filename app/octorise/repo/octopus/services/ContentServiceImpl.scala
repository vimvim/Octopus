package octorise.repo.octopus.services

import org.springframework.stereotype.{Component, Service}
import org.springframework.transaction.annotation.{Propagation, Transactional}
import org.springframework.beans.factory.annotation.{Qualifier, Autowired}

import octorise.repo.octopus.models.{Node, Content}
import octorise.repo.octopus.repositories.{ContentRepo, NodesRepo}


@Service("contentService")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
abstract class ContentServiceImpl[ T<: Content ] extends AbstractNodeService[T] with ContentService[T] {


}
