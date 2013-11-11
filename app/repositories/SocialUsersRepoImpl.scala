package repositories

import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.{Propagation, Transactional}

import models.SocialUser

/**
 *
 */
@Repository("socialUsersRepo")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
class SocialUsersRepoImpl extends AbstractNodesRepo[SocialUser] with SocialUsersRepo {

}
