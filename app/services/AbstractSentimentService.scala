package services

import models.Sentiment

/**
 *
 */
abstract class AbstractSentimentService[ T<: Sentiment ] extends AbstractNodeService[T] with SentimentService[T] {

}
