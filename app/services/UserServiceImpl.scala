package services

import org.springframework.stereotype.Service
import models.User
import repositories.{UsersRepo, SocialUsersRepo, NodesRepo}
import org.springframework.beans.factory.annotation.{Qualifier, Autowired}
import org.springframework.transaction.annotation.{Propagation, Transactional}

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
