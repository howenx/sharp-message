package service;

import entity.order.Order;
import java.util.List;

/**
 * Created by tiffany on 15/12/10.
 */
public interface OrderService {
    /**
     * 订单列表     Added by Tiffany Zhu
     * @return
     */
    List<Order> getOrdersAll();
    /**
     * 订单ajax查询     Added by Tiffany Zhu
     * @param order
     * @return
     */
    List<Order> getOrderPage(Order order);
    /**
     * 由订单ID订单详情   Added by Tiffany Zhu
     * @param orderId
     * @return
     */
    Order getOrderById(Long orderId);
    /**
     * 取消支付超时的订单 Added  by Tiffany Zhu
     * @param orderIds
     */
    void orderCancel(Long[] orderIds);

    /**
     * 超过24小时未支付的订单     Added by Tiffany Zhu 2016.01.05
     * @return
     */
    List<Order> getOutTimeOrders();

    /**
     * 通过拼购活动ID获取订单     Added by Tiffany Zhu 2016.04.09
     * @param pinActivityId
     * @return
     */
    List<Order> getOrderByPinAtvId(Long pinActivityId);

    /**
     * 已支付成功的订单 更新至 成功状态    Added by Tiffany Zhu 2016.04.09
     * @param orderList
     */
    void updPinOrderToSuccess(List<Order> orderList);

    /**
     * 更新订单信息(状态)               Add By Sunny.Wu 2016.04.14
     * @param order 订单
     */
    void updateOrder(Order order);
}
