package octorise.repo.gdrive.models

import javax.persistence.{InheritanceType, Inheritance, Table, Entity}
import org.springframework.beans.factory.annotation.Configurable

import octorise.repo.external.models.{ExternalModelsRepo, ExternalRepo}

/**
 * Represent GDrive external repository model.
 */
@Entity
@Table(name="gdrive_repos")
@Inheritance(strategy=InheritanceType.JOINED)
@Configurable
class GDriveRepo extends ExternalRepo {

}
