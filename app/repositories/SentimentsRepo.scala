package repositories

import models.Sentiment

/**
 *
 */
trait SentimentsRepo[T <: Sentiment] extends NodesRepo[T] {

}
