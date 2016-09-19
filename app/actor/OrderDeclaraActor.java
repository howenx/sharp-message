package actor;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import domain.order.Order;
import domain.order.OrderLine;
import middle.OrderDeclaraMiddle;
import middle.WeiShengExpressMiddle;
import play.Logger;
import service.InventoryService;
import service.OrderLineService;
import service.OrderService;

import javax.inject.Inject;
import java.util.List;

/**
 * 订单申报
 * Created by sibyl.sun on 16/9/12.
 */
public class OrderDeclaraActor extends AbstractActor {

    @Inject
    private OrderService orderService;

    @Inject
    private OrderLineService orderLineService;

    @Inject
    private InventoryService inventoryService;


    @Inject
    public OrderDeclaraActor(OrderDeclaraMiddle orderDeclareMiddle, WeiShengExpressMiddle weiShengExpressMiddle) {
        receive(ReceiveBuilder.match(Long.class, orderId -> {

            Order order = orderService.getOrderById(orderId);
            String orderStatus = order.getOrderStatus();
            List<OrderLine> orderLineList = orderLineService.getLineByOrderId(orderId);
            String invArea = inventoryService.getInventory(orderLineList.get(0).getSkuId()).getInvArea();
            //支付成功的订单 并且申报状态为未申报成功的 并且订单商品为"K" 韩国直邮的  进行订单申报
            if ("S".equals(orderStatus) && !"S".equals(order.getDeclaraStatus()) && "K".equals(invArea)) {
                Logger.error("订单申报 "+orderId);
                orderDeclareMiddle.orderDeclare(orderId);
            } else {
                Logger.error("订单: " + order.getOrderId() + " 的状态:" + order.getOrderStatus() + ", 申报状态:" + order.getDeclaraStatus() + ", 库存区域: " + invArea + " 不符合申报要求");
            }

            //威盛
            weiShengExpressMiddle.weiShengExpress(orderId);

        }).matchAny(s-> {
            Logger.error("order declara error!", s.toString());
            unhandled(s);
        }).build());
    }
}
