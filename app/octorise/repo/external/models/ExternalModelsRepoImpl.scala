package octorise.repo.external.models

import javax.persistence.EntityManager

import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import org.springframework.beans.factory.annotation.Autowired

/**
 * Base externals repos models repository implementation.
 */
@Repository
@Transactional(readOnly = true)
class ExternalModelsRepoImpl @Autowired()(@Autowired em:EntityManager) extends AbstractExternalModelsRepo[ExternalRepo](em) {

}
