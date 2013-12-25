package octorise.repo.octopus.services


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.{Propagation, Transactional}

import octorise.repo.octopus.models.SchemaRef
import octorise.repo.octopus.repositories.SchemaRefsRepo
import octorise.repo.octopus.schema.SchemaDescriptor

/**
 *
 */
@Service("schemaRefService")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
class SchemaRefServiceImpl extends SchemaRefService {

  @Autowired
  var repo: SchemaRefsRepo = _

  def getRef(schema: SchemaDescriptor): SchemaRef = {

    repo.findByName(schema.name) match {

      case Some(schemaRef) => schemaRef.asInstanceOf[SchemaRef]

      case None => {

        val schemaRef = new SchemaRef()
        schemaRef.name = schema.name
        repo.save(schemaRef)

        schemaRef
      }
    }
  }
}
