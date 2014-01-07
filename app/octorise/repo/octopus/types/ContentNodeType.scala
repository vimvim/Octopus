package octorise.repo.octopus.types

import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.{Qualifier, Autowired}

import octorise.repo.octopus.models.{Content, Node}
import octorise.repo.octopus.repositories.{NodesRepo, ContentRepo}
import octorise.repo.octopus.schema.NodeType

/**
 *
 */
@Component("nodeType.Content")
class ContentNodeType @Autowired()(

  @Autowired @Qualifier("nodeType.Node")
  baseType: NodeType[Node],

  @Autowired @Qualifier("contentRepo")
  repo: ContentRepo

) extends NodeType[Content](classOf[Content], "content", baseType, null, repo.asInstanceOf[NodesRepo[Content]])
