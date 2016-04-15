package modules;

import actor.ShopOrderPushActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import middle.ShopOrderMiddle;
import play.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Actor remote call.
 * Created by sibyl.sun on 16/3/1.
 */
@Singleton
public class RemoteActorModule {

    private ActorRef shopOrderPushActor;

    @Inject
    public RemoteActorModule(ActorSystem system, ShopOrderMiddle shopOrderMiddle) {
        shopOrderPushActor = system.actorOf(Props.create(ShopOrderPushActor.class,shopOrderMiddle), "orderPush");
        Logger.info("Started ShopOrderPushActor,path=" + shopOrderPushActor.path());
    }
}
