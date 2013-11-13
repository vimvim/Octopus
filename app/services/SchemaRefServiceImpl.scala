package services

import schema.SchemaDescriptor
import models.SchemaRef
import org.springframework.beans.factory.annotation.Autowired
import repositories.{SchemaRefsRepo, TweetsRepo}
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.{Propagation, Transactional}

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
