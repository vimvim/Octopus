package spring;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import akka.routing.RoundRobinRouter;
import octorise.presenter.Presenter;
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

import octorise.console.SessionsManager;

/**
 *
 */
@Configuration
public class SpringAkkaConfig {

    @Bean
    @Lazy
    public ActorSystem actorSystem() {
        return Akka.system();
    }

    @Bean(name = "presenter")
    @Scope("singleton")
    @Lazy
    public ActorRef presentersDispatcher() {

        List<ActorRef> presenters = new LinkedList<ActorRef>();
        for(int idx=0;idx<5;idx++) {
            presenters.add(SpringAkka.createActor(actorSystem(), Presenter.class));
        }

        return actorSystem().actorOf(Props.empty().withRouter(RoundRobinRouter.create(presenters)));
    }

    @Bean(name = "consoleSessionManager")
    @Scope("prototype")
    @Lazy
    public ActorRef consoleSessionManager() {
        return SpringAkka.createActor(actorSystem(), SessionsManager.class);
    }

    @Bean(name = "topsyTweetsParser")
    @Scope("prototype")
    @Lazy
    public ActorRef topsyTweetsParser() {

        // TODO: Refactor using SpringAkka

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

        // TODO: Refactor using SpringAkka

        List args = new ArrayList();
        args.add(FileBatchLoader.class);
        args.add("");

        return actorSystem().actorOf(Props.create(SpringActorProducer.class, scala.collection.JavaConversions.asScalaBuffer(args)));

        // actorSystem.actorOf(Props.apply(classOf[SpringActorProducer], classOf[FileBatchLoader]))

        // actorSystem.actorOf(Props.create(classOf[SpringActorProducer], classOf[TopsyTweetsParser]))

        // return null;
    }
}
