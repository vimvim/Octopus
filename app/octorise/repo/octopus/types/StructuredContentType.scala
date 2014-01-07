package octorise.repo.octopus.types

import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.{Qualifier, Autowired}

import octorise.repo.octopus.schema.NodeType
import octorise.repo.octopus.models.{StructuredContent, Content}
import octorise.repo.octopus.services.StructuredContentService
import octorise.repo.octopus.repositories.StructuredContentRepo

@Component("nodeType.StructuredContent")
class StructuredContentType @Autowired()(

  @Autowired @Qualifier("nodeType.Content")
  baseType: NodeType[Content],

  @Autowired @Qualifier("structuredContentService")
  service: StructuredContentService,

  @Autowired @Qualifier("structuredContentRepo")
  repo: StructuredContentRepo

) extends NodeType[StructuredContent](classOf[StructuredContent], "content", baseType, service, repo)
