package mapper;

import domain.order.Order;
import domain.order.OrderCustoms;

import java.util.List;

/**
 * 订单
 * Created by tiffany on 15/12/10.
 */
public interface OrderMapper {
    /**
     * 订单列表     Added by Tiffany Zhu
     * @return
     */
    List<Order> getOrdersAll();

    /**
     * ajax分页查询     Added by Tiffany Zhu
     * @param order
     * @return
     */
    List<Order> getOrderPage(Order order);

    /**
     * 由订单ID获取订单详情      Added by Tiffany Zhu
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
     * 更新订单信息(状态)              Add By Sunny.Wu 2016.04.14
     * @param order 订单
     */
    void updateOrder(Order order);

    /**
     * 修改订单推送到ERP的状态         Add By Sunny.Wu 2016.09.05
     * @param order 订单
     */
    void updateOrderERPStatus(Order order);

    /**
     * 更新订单申报状态               Add By Sibyl.sun 2016.09.13
     * @param order 订单
     */
    void updateOrderDeclaraStatus(Order order);

    /**
     * 更新威盛物流状态               Add By Sibyl.sun 2016.09.13
     * @param order 订单
     */
    void updateOrderExpressStatus(Order order);

    /**
     * 更新订单申报数据    Added By Sunny Wu 2016.09.19
     * @param orderCustoms 订单申报
     */
    void updateOrderCustoms(OrderCustoms orderCustoms);

    /**
     * 根据订单ID获取一条订单申报信息    Added By Sunny Wu 2016.09.19
     * @param orderId 订单号
     * @return OrderCustoms
     */
    OrderCustoms getOrderCustomsById(Long orderId);

}
