package octorise.repo.octopus.repositories

import scala.reflect.Manifest

import org.springframework.transaction.annotation.{Propagation, Transactional}

import octorise.repo.octopus.models.Content

/**
 *
 */
@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
abstract class AbstractContentRepo[ T<: Content: Manifest] extends AbstractNodesRepo[T] with ContentRepo[T] {

}
