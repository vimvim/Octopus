package repositories

import javax.persistence.EntityManager

import org.springframework.beans.factory.annotation.Autowired

import models.{Node, User}

/**
 *
 */
abstract class AbstractNodesRepo[T <: Node](implicit m: Manifest[T]) extends NodesRepo[T] {

  @Autowired
  var entityManager: EntityManager = _

  def entityClass = m.erasure.asInstanceOf[Class[T]]

  def save(user: T): Unit = user.id match {
    case 0 => entityManager.persist(user)
    case _ => entityManager.merge(user)
  }

  def find(id: Int): Option[T] = {
    Option(entityManager.find(entityClass, id))

    // Option(entityManager.find(T, id))
    // Option(entityManager.find(classOf[T].getClass, id))
    // Option(entityManager.find(classOf[Class[T]], id))
    // Option(entityManager.find(classOf[T], id))


    // Option(entityManager.find(classManifest[T].erasure, id))

    // val v = entityManager.find(classManifest[T].erasure, id);
    // cast[classManifest[T]](v)
  }

  // def getAll: List[T] = {
  //  entityManager.createQuery("From User", classOf[T]).getResultList.toList
  // }

  def cast[A : Manifest](value: Any): Option[A] = {
    val erasure = manifest[A] match {
      case Manifest.Byte => classOf[java.lang.Byte]
      case Manifest.Short => classOf[java.lang.Short]
      case Manifest.Char => classOf[java.lang.Character]
      case Manifest.Long => classOf[java.lang.Long]
      case Manifest.Float => classOf[java.lang.Float]
      case Manifest.Double => classOf[java.lang.Double]
      case Manifest.Boolean => classOf[java.lang.Boolean]
      case Manifest.Int => classOf[java.lang.Integer]
      case m => m.erasure
    }
    if(erasure.isInstance(value)) Some(value.asInstanceOf[A]) else None
  }

}
