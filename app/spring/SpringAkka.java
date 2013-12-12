package spring;

import akka.actor.Actor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class SpringAkka {

    public static ActorRef createActor(ActorSystem actorSystem, String beanName) {

        List args = new ArrayList();
        args.add(Actor.class);
        args.add(beanName);
        args.add(null);

        return actorSystem.actorOf(Props.create(SpringActorProducer.class, scala.collection.JavaConversions.asScalaBuffer(args)));
    }

    public static ActorRef createActor(ActorSystem actorSystem, String beanName, List actorArgs) {

        List args = new ArrayList();
        args.add(Actor.class);
        args.add(beanName);
        args.add(actorArgs);

        return actorSystem.actorOf(Props.create(SpringActorProducer.class, scala.collection.JavaConversions.asScalaBuffer(args)));
    }

    public static ActorRef createActor(ActorSystem actorSystem, Class<? extends Actor> actorClass) {

        List args = new ArrayList();
        args.add(actorClass);
        args.add("");
        args.add(null);

        return actorSystem.actorOf(Props.create(SpringActorProducer.class, scala.collection.JavaConversions.asScalaBuffer(args)));
    }

    public static ActorRef createActor(ActorSystem actorSystem, Class<? extends Actor> actorClass, List actorArgs) {

        List args = new ArrayList();
        args.add(actorClass);
        args.add("");
        args.add(actorArgs);

        return actorSystem.actorOf(Props.create(SpringActorProducer.class, scala.collection.JavaConversions.asScalaBuffer(args)));
    }
}
