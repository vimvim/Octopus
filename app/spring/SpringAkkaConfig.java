package spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import play.libs.Akka;

import actors.FileBatchLoader;
import actors.TopsyTweetsParser;

/**
 *
 */
@Configuration
public class SpringAkkaConfig {

    @Bean
    public ActorSystem actorSystem() {
        return Akka.system();
    }

    @Bean(name = "topsyTweetsParser")
    @Scope("prototype")
    @Lazy
    public ActorRef topsyTweetsParser() {

        List args = new ArrayList();
        args.add(TopsyTweetsParser.class);
        args.add("");

        return actorSystem().actorOf(Props.create(SpringActorProducer.class, scala.collection.JavaConversions.asScalaBuffer(args)));

        // actorSystem.actorOf(Props.apply(classOf[SpringActorProducer], classOf[TopsyTweetsParser]))

        // actorSystem.actorOf(Props.create(classOf[SpringActorProducer], classOf[TopsyTweetsParser]))

        // return null;
    }

    @Bean(name = "fileBatchLoader")
    @Scope("prototype")
    @Lazy
    public ActorRef fileBatchLoader()  {

        List args = new ArrayList();
        args.add(FileBatchLoader.class);
        args.add("");

        return actorSystem().actorOf(Props.create(SpringActorProducer.class, scala.collection.JavaConversions.asScalaBuffer(args)));

        // actorSystem.actorOf(Props.apply(classOf[SpringActorProducer], classOf[FileBatchLoader]))

        // actorSystem.actorOf(Props.create(classOf[SpringActorProducer], classOf[TopsyTweetsParser]))

        // return null;
    }
}
