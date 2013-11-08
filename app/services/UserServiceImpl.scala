package services

import org.springframework.stereotype.Service
import models.User

/**
 *
 */
@Service("userService")
class UserServiceImpl extends UserService {

  def create(): User = {
    new User
  }

}
