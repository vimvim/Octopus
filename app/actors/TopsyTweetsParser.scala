package actors

import java.io.{FileReader, BufferedReader, File}

import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.{Qualifier, Autowired}
import org.springframework.context.annotation.{Scope, Lazy}
import org.springframework.transaction.annotation.{Propagation, Transactional}

import akka.actor.Actor

import org.codehaus.jackson.map.ObjectMapper
import org.codehaus.jackson.JsonNode
import octorise.repo.octopus.services.TweetService


/**
 * TODO: Check and remove Component annotation because component is handled by SpringAkkaConfig
 */
@Component("TopsyTweetsParser.Actor")
@Scope("prototype")
@Lazy
class TopsyTweetsParser extends Actor {

  var tweetService: TweetService = null

  @Autowired def this(
                       @Qualifier("tweetService") tweetService: TweetService
                     ) {

    this()

    this.tweetService = tweetService
  }

  def receive: Actor.Receive = {

    case LoadDataChunk(line) => {

      val mapper: ObjectMapper = new ObjectMapper
      val rootNode = mapper.readTree(line)

      val tweetId: String = rootNode.path("id_str").getTextValue
      val tweetText: String = rootNode.path("text").getTextValue

      val entities: JsonNode = rootNode.path("entities")
      val user: JsonNode = rootNode.path("user")
      val topsy: JsonNode = rootNode.path("topsy")

      val userID = user.path("id").getIntValue
      val screenName: String = user.path("screen_name").getTextValue
      val userName: String = user.path("name").getTextValue

      tweetService.createTweet(userID, screenName, userName, tweetId, tweetText)
    }
  }

}
