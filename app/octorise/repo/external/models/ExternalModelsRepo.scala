package octorise.repo.external.models

import org.springframework.data.jpa.repository.JpaRepository

/**
 * Base repository for models which is represent external repositories.
 */
trait ExternalModelsRepo[ T<:ExternalRepo ] extends JpaRepository[T, java.lang.Long] {

  def find(id: Int): Option[T]

  def findByName(name:String): Option[T]

}
