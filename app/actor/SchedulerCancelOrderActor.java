package actor;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import domain.order.Order;
import play.Logger;
import service.OrderService;

import javax.inject.Inject;

/**
 * 用于定时取消订单的Actor
 * Created by howen on 15/12/24.
 */
public class SchedulerCancelOrderActor extends AbstractActor {

    @Inject
    public SchedulerCancelOrderActor(OrderService cartService) {
        receive(ReceiveBuilder.match(Long.class, orderId -> {
            Order orders =cartService.getOrderById(orderId);
            Logger.error("撒发生的发生地方--->\n"+orders.toString());
        }).matchAny(s -> {
            Logger.error("SchedulerCancelOrderActor received messages not matched: {}", s.toString());
            unhandled(s);
        }).build());
    }
}
