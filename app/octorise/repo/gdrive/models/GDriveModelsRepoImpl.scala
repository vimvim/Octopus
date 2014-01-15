package octorise.repo.gdrive.models

import javax.persistence.EntityManager

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

import octorise.repo.external.models.AbstractExternalModelsRepo


/**
 * Repository for GDriveRepo
 */
@Repository
@Transactional(readOnly = true)
class GDriveModelsRepoImpl @Autowired()(@Autowired em:EntityManager) extends AbstractExternalModelsRepo[GDriveRepo](em) with GDriveModelsRepo {

}
