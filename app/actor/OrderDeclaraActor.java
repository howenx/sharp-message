package actor;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import middle.OrderDeclaraMiddle;
import play.Logger;

import javax.inject.Inject;

/**
 * 订单申报
 * Created by sibyl.sun on 16/9/12.
 */
public class OrderDeclaraActor extends AbstractActor {

    @Inject
    public OrderDeclaraActor(OrderDeclaraMiddle orderDeclareMiddle) {
        receive(ReceiveBuilder.match(Long.class, orderId -> {
            Logger.error("订单申报 "+orderId);

            orderDeclareMiddle.orderDeclare(orderId);

        }).matchAny(s-> {
            Logger.error("ERP order query error!", s.toString());
            unhandled(s);
        }).build());
    }
}
