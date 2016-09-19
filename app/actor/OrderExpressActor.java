package actor;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import domain.order.Order;
import domain.order.OrderCustoms;
import domain.order.OrderLine;
import middle.WeiShengExpressMiddle;
import play.Logger;
import service.InventoryService;
import service.OrderLineService;
import service.OrderService;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by sibyl.sun on 16/9/19.
 */
public class OrderExpressActor extends AbstractActor {
    @Inject
    private OrderService orderService;

    @Inject
    private OrderLineService orderLineService;

    @Inject
    private InventoryService inventoryService;


    @Inject
    public OrderExpressActor( WeiShengExpressMiddle weiShengExpressMiddle) {
        receive(ReceiveBuilder.match(Long.class, orderId -> {

            Order order = orderService.getOrderById(orderId);
            OrderCustoms orderCustoms = orderService.getOrderCustomsById(orderId);
            String orderStatus = order.getOrderStatus();
            List<OrderLine> orderLineList = orderLineService.getLineByOrderId(orderId);
            String invArea = inventoryService.getInventory(orderLineList.get(0).getSkuId()).getInvArea();
            //支付成功的订单 并且物流推送状态为未成功成功的 并且订单商品为"K" 韩国直邮的  推送物流信息
            if ("S".equals(orderStatus) && !"S".equals(orderCustoms.getExpressStatus()) && "K".equals(invArea)) {
                Logger.error("订单推送物流信息: "+orderId);
                //威盛
                weiShengExpressMiddle.weiShengExpress(orderId);
            }
            else {
                Logger.error("订单: " + order.getOrderId() + " 的状态:" + order.getOrderStatus() + ", 物流推送状态:" + orderCustoms.getExpressStatus() + ", 库存区域: " + invArea + " 不符合物流推送要求");
            }

        }).matchAny(s-> {
            Logger.error("order express push error!", s.toString());
            unhandled(s);
        }).build());
    }
}
