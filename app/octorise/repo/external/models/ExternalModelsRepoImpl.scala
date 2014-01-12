package octorise.repo.external.models

import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

/**
 *
 */
@Repository
@Transactional(readOnly = true)
class ExternalModelsRepoImpl extends AbstractExternalModelsRepo[ExternalRepo] {

}
