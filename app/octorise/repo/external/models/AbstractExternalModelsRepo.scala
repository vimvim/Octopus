package octorise.repo.external.models

import scala.reflect.Manifest

import javax.persistence.EntityManager
import javax.persistence.criteria.{Predicate, CriteriaBuilder, CriteriaQuery, Root}
import javax.persistence.metamodel.{SingularAttribute, StaticMetamodel}

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.support.SimpleJpaRepository
import org.springframework.transaction.annotation.Transactional
import org.springframework.data.jpa.domain.Specification


/**
 *
 */
@Transactional(readOnly = true)
abstract class AbstractExternalModelsRepo[ T <: ExternalRepo: Manifest ](val entityClass: Class[T], em:EntityManager)
  extends SimpleJpaRepository[T, java.lang.Long](entityClass, em) with ExternalModelsRepo[T] {

  // @Autowired
  // var entityManager: EntityManager = _

  def this(em:EntityManager) = this(manifest[T].erasure.asInstanceOf[Class[T]], em)

  protected def hasName(name:String) = {
    new Specification[T]() {
      def toPredicate(root: Root[T], query: CriteriaQuery[_], cb: CriteriaBuilder): Predicate = cb.equal(root.get(ExternalRepo_.name), name)
    }
  }

  def find(id: Int): Option[T] = {
    val res = findOne(id)
    if (res==null) None else Some(res)
  }

  def findByName(name:String): Option[T] = {
    val res = findOne(hasName(name))
    if (res==null) None else Some(res)
  }

}
