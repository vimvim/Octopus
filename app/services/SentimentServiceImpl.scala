package services

import models.{Node, Sentiment}

/**
 *
 */
class SentimentServiceImpl extends AbstractSentimentService[Sentiment] {

  def create(): Sentiment = {
    throw new NotImplementedError("Create method is not implemented for general sentiment type")
  }

}
