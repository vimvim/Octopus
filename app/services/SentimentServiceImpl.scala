package services

import models.{Node, Sentiment}
import org.springframework.stereotype.Service

/**
 *
 */
@Service("sentimentService")
class SentimentServiceImpl extends AbstractSentimentService[Sentiment] {

  def create(): Sentiment = {
    throw new NotImplementedError("Create method is not implemented for general sentiment type")
  }

}
