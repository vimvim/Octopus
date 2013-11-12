package repositories

import models.Sentiment

/**
 *
 */
abstract class AbstractSentimentsRepo[T <: Sentiment :Manifest] extends AbstractNodesRepo[T] with SentimentsRepo[T] {

}
