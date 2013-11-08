package repositories

import models.User

/**
 *
 */
trait UsersRepo extends NodesRepo[User] {

  def getByUsername(lastName : String): List[User]
}
