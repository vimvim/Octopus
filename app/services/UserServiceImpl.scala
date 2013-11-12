package services

import org.springframework.stereotype.Service
import models.User
import repositories.{UsersRepo, SocialUsersRepo, NodesRepo}
import org.springframework.beans.factory.annotation.{Qualifier, Autowired}

/**
 *
 */
@Service("userService")
class UserServiceImpl extends AbstractNodeService[User] with UserService {

  @Autowired
  @Qualifier("usersRepo")
  var _repo: UsersRepo = _

  def repo(): NodesRepo[User] = _repo

  protected def createEntity(): User = new User()
}
