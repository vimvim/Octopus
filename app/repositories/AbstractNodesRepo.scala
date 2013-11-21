package repositories

import scala.collection.JavaConverters._

import javax.persistence.EntityManager

import org.springframework.beans.factory.annotation.{Qualifier, Autowired}

import models.{SchemaRef, Attribute, Node, User}
import scala.reflect._
import scala.Some
import scala.Predef._
import scala.Some
import scala.reflect.Manifest

/**
 *
 */
// abstract class AbstractNodesRepo[T <: Node](implicit m: Manifest[T]) extends NodesRepo[T] {
abstract class AbstractNodesRepo[T <: Node: Manifest](val entityClass: Class[T]) extends NodesRepo[T] {

  @Autowired
  @Qualifier("schemaRefsRepo")
  var schemaResRepo: SchemaRefsRepo = _

  @Autowired
  var entityManager: EntityManager = _

  // def entityClass = m.erasure.asInstanceOf[Class[T]]

  def this() = this(manifest[T].erasure.asInstanceOf[Class[T]])

  def save(user: T): Unit = user.id match {
    case 0 => entityManager.persist(user)
    case _ => entityManager.merge(user)
  }

  def fetch(offset:Int, limit:Int):List[T] = {

    val entityName = entityClass.getSimpleName

    val query = entityManager.createQuery("SELECT COUNT(*) FROM "+entityName)
    query.setFirstResult(offset)
    query.setMaxResults(limit)

    val res = query.getResultList

    res.asScala.asInstanceOf[List[T]]
  }

  def totalCount: Int = {

    val entityName = entityClass.getSimpleName

    val query = entityManager.createQuery("SELECT COUNT(*) FROM "+entityName)

    val res = query.getSingleResult
    res.asInstanceOf[Int]
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

  def findOneBySchemaAttrValue[VT: ClassTag](schemaName: String, attrName: String, value: VT): Option[T] = {

    def find(schemaRef: SchemaRef, attrName: String, attrType: String, value: VT): Option[T] = {

      val query = entityManager.createQuery("SELECT attr FROM "+attrType+" attr WHERE attr.name=:attrName AND attr.value=:attrValue AND attr.schemaRef=:schemaRef")
      query.setParameter("schemaRef", schemaRef)
      query.setParameter("attrName", attrName)
      query.setParameter("attrValue", value)

      val res = query.getResultList
      if (res.size()>0) {

        val attr = res.get(0).asInstanceOf[Attribute[VT]]
        val node = attr.node

        this.find(node.id)
        /*

        if (node.isInstanceOf[T]) {
          Some(attr.node.asInstanceOf[T])
        } else {
          None
        }
        */
      } else {
        None
      }
    }

    schemaResRepo.findByName(schemaName) match {

      case Some(schemaRef) => {

        val NodeClassTag = classTag[Node]
        val StringClassTag = classTag[String]

        implicitly[ClassTag[VT]] match {
          case StringClassTag => find(schemaRef, attrName, "AttributeString", value)
          case ClassTag.Int => find(schemaRef, attrName, "AttributeInt", value)
          case ClassTag.Boolean => find(schemaRef, attrName, "AttributeBool", value)
          case NodeClassTag => find(schemaRef, attrName, "AttributeNode", value)
          case _ => throw new Exception("Unexpected attribute value type")
        }
      }

      case None => None
    }
  }

  private def cast[A : Manifest](value: Any): Option[A] = {
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
