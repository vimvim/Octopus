package octorise.repo.octopus.types

import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.{Qualifier, Autowired}

import octorise.repo.octopus.schema.NodeType
import octorise.repo.octopus.models.{TextContent, StructuredContent, Content}
import octorise.repo.octopus.repositories.TextContentRepo
import octorise.repo.octopus.services.TextContentService

/**
 *
 */
@Component("nodeType.TextContent")
class TextContentType @Autowired()(

  @Autowired @Qualifier("nodeType.Content")
  baseType: NodeType[Content],

  @Autowired @Qualifier("textContentService")
  service: TextContentService,

  @Autowired @Qualifier("textContentRepo")
  repo: TextContentRepo

) extends NodeType[TextContent](classOf[TextContent], "content", baseType, service, repo)
