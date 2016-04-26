package actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.japi.pf.ReceiveBuilder;
import controllers.AdminUserCtrl;
import domain.order.Order;
import middle.ShopOrderMiddle;
import modules.LevelFactory;
import modules.NewScheduler;
import play.Logger;
import play.libs.Json;
import scala.concurrent.duration.Duration;
import service.OrderService;
import service.OrderSplitService;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.concurrent.TimeUnit;

/**
 * 往ERP推送订单的Actor
 * Created by Sunny Wu on 16/3/7.
 * kakao china.
 */
public class ShopOrderPushActor extends AbstractActor{

    @Inject
    private OrderService orderService;

    @Inject
    private OrderSplitService orderSplitService;

    @Inject
    private NewScheduler scheduler;

    @Inject
    private LevelFactory levelFactory;

    @Inject
    @Named("salesOrderQueryActor")
    private ActorRef salesOrderQueryActor;

    @Inject
    public ShopOrderPushActor(ShopOrderMiddle shopOrderMiddle) {
        receive(ReceiveBuilder.match(Long.class, orderId -> {
            Order order = orderService.getOrderById(orderId);
            String orderStatus = order.getOrderStatus();
            //支付成功的订单
            if ("S".equals(orderStatus)) {
                String shopOrderNo = shopOrderMiddle.shopOrderPush(orderId);
                Logger.error("推送结果:"+shopOrderNo);
                //推送成功的订单再创建schedule
                if (Json.parse(shopOrderNo).has("ShopOrderNo")) {
                    shopOrderNo = Json.parse(shopOrderNo).findValue("ShopOrderNo").toString();
                    Logger.error("订单"+shopOrderNo+":push to ERP");
                    //启动scheduler从erp查询订单,海关审核通过,更新物流信息
                    scheduler.schedule(Duration.create(AdminUserCtrl.ORDER_QUERY_DELAY, TimeUnit.MILLISECONDS),Duration.create(AdminUserCtrl.ORDER_QUERY_INTERVAL, TimeUnit.MILLISECONDS),salesOrderQueryActor,orderId);
                }
            }
        }).matchAny(s-> {
            Logger.error("push to ERP error!", s.toString());
            unhandled(s);
        }).build());
    }
}
