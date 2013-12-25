package octorise.repo.octopus.repositories

import org.springframework.beans.factory.annotation._
import org.springframework.stereotype._
import org.springframework.transaction.annotation.{Propagation, Transactional}
import javax.persistence._
import scala.collection.JavaConversions._

import octorise.repo.octopus.models.User

/**
 *
 */
@Repository("usersRepo")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
class UsersRepoImpl extends AbstractNodesRepo[User] with UsersRepo {

  def getByUsername(lastName : String): List[User] = {
    entityManager.createQuery("From User Where username = :username", classOf[User]).setParameter("username", lastName).getResultList.toList
  }

}
