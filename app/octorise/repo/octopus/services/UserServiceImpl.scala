package octorise.repo.octopus.services

import org.springframework.stereotype.Service
import org.springframework.beans.factory.annotation.{Qualifier, Autowired}
import org.springframework.transaction.annotation.{Propagation, Transactional}

import octorise.repo.octopus.models.User
import octorise.repo.octopus.repositories.{NodesRepo, UsersRepo}

/**
 *
 */
@Service("userService")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
class UserServiceImpl extends AbstractNodeService[User] with UserService {

  @Autowired
  @Qualifier("usersRepo")
  var _repo: UsersRepo = _

  def repo(): NodesRepo[User] = _repo

  protected def createEntity(): User = new User()
}
