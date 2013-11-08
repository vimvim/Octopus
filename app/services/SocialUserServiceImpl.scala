package services

import models.{SocialUser, User, Node}
import repositories.{SocialUsersRepo, NodesRepo}
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

/**
 *
 */
@Service("socialUserService")
class SocialUserServiceImpl extends AbstractNodeService[SocialUser] with SocialUserService {

  @Autowired
  var _repo: SocialUsersRepo = _

  def repo(): SocialUsersRepo = {
    _repo
  }

  protected def createEntity(): SocialUser = {
    new SocialUser
  }



}
