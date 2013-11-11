package repositories

import models.Sentiment

/**
 *
 */
abstract class AbstractSentimentsRepo[T <: Sentiment] extends AbstractNodesRepo[T] with SentimentsRepo[T] {

}
