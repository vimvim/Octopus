package octorise.repo.external.models

import scala.reflect.Manifest

import javax.persistence.EntityManager

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.repository.support.SimpleJpaRepository
import org.springframework.transaction.annotation.Transactional

/**
 *
 */
@Transactional(readOnly = true)
abstract class AbstractExternalModelsRepo[ T <: ExternalRepo: Manifest ](val entityClass: Class[T], em:EntityManager)
  extends SimpleJpaRepository[T, Long](entityClass, em) with ExternalModelsRepo[T] {

  @Autowired
  var entityManager: EntityManager = _

  def this() = this(manifest[T].erasure.asInstanceOf[Class[T]], entityManager)

  def find(id: Int): Option[T] = {

  }

  def findByName(name:String): Option[T] = {

  }

}
