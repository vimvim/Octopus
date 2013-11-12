package repositories

import models.SchemaRef
import org.springframework.beans.factory.annotation.Autowired
import javax.persistence.EntityManager
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.{Propagation, Transactional}

/**
 *
 */
@Repository("schemaRefsRepo")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
class SchemaRefsRepoImpl extends SchemaRefsRepo {

  @Autowired
  var entityManager: EntityManager = _

  def save(schemaRef: SchemaRef): Unit = schemaRef.id match {
    case 0 => entityManager.persist(schemaRef)
    case _ => entityManager.merge(schemaRef)
  }

  def findByName(schemaName: String): Option[SchemaRef] = {

    val query = entityManager.createQuery("SELECT schemaRef FROM SchemaRef schemaRef WHERE schemaRef.name=:schemaName")
    val res = query.getResultList
    if (res.size()>0) {
      Some(res.get(0).asInstanceOf[SchemaRef])
    } else {
      None
    }
  }

}
