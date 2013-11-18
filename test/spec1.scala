
import actors.TopsyTweetsParser
import java.io.File
import play.api.test.{FakeApplication, WithApplication, PlaySpecification}
import play.api.test.Helpers._
import org.specs2.mutable._
import spring.SpringContextHolder


class spec1 extends Specification {

  "The 'Hello world' string" should {
    "contain 11 characters" in {
      "Hello world" must have size(11)
    }
    "start with 'Hello'" in {
      "Hello world" must startWith("Hello")
    }
    "end with 'world'" in {
      "Hello world" must endWith("world")
    }
  }

  "The specification" should {

    "have access to HeaderNames" in new WithApplication {

      val file= new File("/home/vim/Dropbox/Projects/sentiment/tweets_airines_1.json")

      val tweetsHandler = SpringContextHolder.getContext.getBean(classOf[TopsyTweetsParser])
      tweetsHandler.loadTweets(file)

      println("1")

      running(FakeApplication()) {

        println("1")

        SpringContextHolder.getContext.start()

        val file= new File("/home/vim/Dropbox/Projects/sentiment/tweets_airines_1.json")

        val tweetsHandler = SpringContextHolder.getContext.getBean(classOf[TopsyTweetsParser])
        tweetsHandler.loadTweets(file)

        println("3")

      }
    }
  }
}