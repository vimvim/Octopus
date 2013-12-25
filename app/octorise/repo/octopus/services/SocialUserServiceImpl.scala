package octorise.repo.octopus.services

import org.springframework.beans.factory.annotation.{Qualifier, Autowired}
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.{Propagation, Transactional}

import octorise.repo.octopus.models.SocialUser
import octorise.repo.octopus.repositories.SocialUsersRepo

/**
 *
 */
@Service("socialUserService")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
class SocialUserServiceImpl extends AbstractNodeService[SocialUser] with SocialUserService {

  @Autowired
  @Qualifier("socialUsersRepo")
  var _repo: SocialUsersRepo = _

  def repo(): SocialUsersRepo = {
    _repo
  }

  protected def createEntity(): SocialUser = {
    new SocialUser
  }



}
