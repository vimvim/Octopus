package octorise.repo.octopus.services

import octorise.repo.octopus.models.Sentiment

/**
 *
 */
abstract class AbstractSentimentService[ T<: Sentiment ] extends AbstractNodeService[T] with SentimentService[T] {

}
