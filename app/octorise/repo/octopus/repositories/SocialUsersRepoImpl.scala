package octorise.repo.octopus.repositories

import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.{Propagation, Transactional}

import octorise.repo.octopus.models.SocialUser

/**
 *
 */
@Repository("socialUsersRepo")
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
class SocialUsersRepoImpl extends AbstractNodesRepo[SocialUser] with SocialUsersRepo {

}
