package octorise.repo.octopus.repositories

import octorise.repo.octopus.models.Sentiment

/**
 *
 */
abstract class AbstractSentimentsRepo[T <: Sentiment :Manifest] extends AbstractNodesRepo[T] with SentimentsRepo[T] {

}
