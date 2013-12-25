package octorise.repo.octopus.repositories

import octorise.repo.octopus.models.User

/**
 *
 */
trait UsersRepo extends NodesRepo[User] {

  def getByUsername(lastName : String): List[User]
}
