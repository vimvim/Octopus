package repositories

import org.springframework.beans.factory.annotation._
import org.springframework.stereotype._
import org.springframework.transaction.annotation.{Propagation, Transactional}
import javax.persistence._
import scala.collection.JavaConversions._

import models.User

/**
 *
 */
@Repository("usersDao")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
class UsersDaoImpl extends UsersDao {

  @Autowired
  var entityManager: EntityManager = _

  def save(user: User): Unit = user.id match {
    case 0 => entityManager.persist(user)
    case _ => entityManager.merge(user)
  }

  def find(id: Int): Option[User] = {
    Option(entityManager.find(classOf[User], id))
  }

  def getAll: List[User] = {
    entityManager.createQuery("From User", classOf[User]).getResultList.toList
  }

  def getByUsername(lastName : String): List[User] = {
    entityManager.createQuery("From User Where username = :username", classOf[User]).setParameter("username", lastName).getResultList.toList
  }

}
