package octorise.repo.gdrive.models

import org.springframework.stereotype.Repository

import octorise.repo.external.models.{AbstractExternalModelsRepo, ExternalModelsRepo}

/**
 * Repository for GDriveRepo
 */
@Repository
class GDriveModelsRepoImpl extends AbstractExternalModelsRepo[GDriveRepo] with GDriveModelsRepo {

}

