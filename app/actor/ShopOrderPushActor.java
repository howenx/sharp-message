package actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.japi.pf.ReceiveBuilder;
import domain.order.Order;
import domain.order.OrderLine;
import middle.ShopOrderMiddle;
import modules.LevelFactory;
import modules.NewScheduler;
import play.Logger;
import play.libs.Json;
import scala.concurrent.duration.Duration;
import service.InventoryService;
import service.OrderLineService;
import service.OrderService;
import service.OrderSplitService;
import util.SysParCom;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
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
    private OrderLineService orderLineService;

    @Inject
    private NewScheduler scheduler;

    @Inject
    private LevelFactory levelFactory;

    @Inject
    private InventoryService inventoryService;

    @Inject
    @Named("salesOrderQueryActor")
    private ActorRef salesOrderQueryActor;

    @Inject
    public ShopOrderPushActor(ShopOrderMiddle shopOrderMiddle) {
        receive(ReceiveBuilder.match(Long.class, orderId -> {
            Order order = orderService.getOrderById(orderId);
            String orderStatus = order.getOrderStatus();
            List<OrderLine> orderLineList = orderLineService.getLineByOrderId(orderId);
            String invArea = inventoryService.getInventory(orderLineList.get(0).getSkuId()).getInvArea();
            //支付成功的订单 并且推送状态为未推送成功的 并且订单商品为"K" 韩国直邮的  推送到ERP
            if ("S".equals(orderStatus) && !"S".equals(order.getErpStatus()) && "K".equals(invArea)) {
                String shopOrderNo = shopOrderMiddle.shopOrderPush(orderId);
                Logger.error("推送结果:"+shopOrderNo);
                //推送成功的订单再创建schedule
                if (Json.parse(shopOrderNo).has("ShopOrderNo")) {
                    shopOrderNo = Json.parse(shopOrderNo).findValue("ShopOrderNo").toString();
                    order.setErpStatus("S");
                    Logger.error("订单"+shopOrderNo+":push to ERP success");
                    //启动scheduler从erp查询订单,海关审核通过,更新物流信息
                    scheduler.schedule(Duration.create(SysParCom.ORDER_QUERY_DELAY, TimeUnit.MILLISECONDS),Duration.create(SysParCom.ORDER_QUERY_INTERVAL, TimeUnit.MILLISECONDS),salesOrderQueryActor,orderId);
                }
                else {//订单推送到ERP失败
                    order.setErpStatus("F");
                    Logger.error("订单"+shopOrderNo+":push to ERP fail");
                }
                orderService.updateOrderERPStatus(order);
            } else {
                Logger.error("订单: " + order.getOrderId() + " 的状态:" + order.getOrderStatus() + ", 推送状态:" + order.getErpStatus() + ", 库存区域:" + invArea + " 不符合推送要求");
            }
        }).matchAny(s-> {
            Logger.error("push to ERP error!", s.toString());
            unhandled(s);
        }).build());
    }
}
