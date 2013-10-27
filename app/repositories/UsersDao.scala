package repositories

import models.User

/**
 *
 */
trait UsersDao {
  def save(user: User): Unit

  def find(id: Int): Option[User]

  def getAll: List[User]

  def getByUsername(lastName : String): List[User]
}
