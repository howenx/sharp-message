package service;

import domain.order.Order;
import mapper.OrderMapper;
import javax.inject.Inject;
import java.util.List;


/**
 * Created by tiffany on 15/12/10.
 */
public class OrderServiceImpl implements OrderService {
    @Inject
    private OrderMapper orderMapper;

    /**
     * 订单列表     Added by Tiffany Zhu
     * @return
     */
    @Override
    public List<Order> getOrdersAll(){return orderMapper.getOrdersAll();}

    /**
     * 订单ajax查询     Added by Tiffany Zhu
     * @param order
     * @return
     */
    @Override
    public List<Order> getOrderPage(Order order) {
        return orderMapper.getOrderPage(order);
    }

    /**
     * 由订单ID订单详情   Added by Tiffany Zhu
     * @param orderId
     * @return
     */
    @Override
    public Order getOrderById(Long orderId) {return orderMapper.getOrderById(orderId);}

    /**
     * 取消支付超时的订单 Added  by Tiffany Zhu
     * @param orderIds
     */
    @Override
    public void orderCancel(Long[] orderIds) {

        orderMapper.orderCancel(orderIds);

    }

    /**
     * 超过24小时未支付的订单     Added by Tiffany Zhu 2016.01.05
     * @return
     */
    @Override
    public List<Order> getOutTimeOrders() {
        return orderMapper.getOutTimeOrders();
    }

    /**
     * 通过拼购活动ID获取订单     Added by Tiffany Zhu 2016.04.09
     * @param pinActivityId
     * @return
     */
    @Override
    public List<Order> getOrderByPinAtvId(Long pinActivityId) {
        return orderMapper.getOrderByPinAtvId(pinActivityId);
    }

    /**
     * 已支付成功的订单 更新至 成功状态    Added by Tiffany Zhu 2016.04.09
     * @param orderList
     */
    @Override
    public void updPinOrderToSuccess(List<Order> orderList) {
        orderMapper.updPinOrderToSuccess(orderList);
    }

    /**
     * 更新订单信息(状态)               Add By Sunny.Wu 2016.04.14
     * @param order 订单
     */
    public void updateOrder(Order order) {
        orderMapper.updateOrder(order);
    }

    /**
     * 修改订单推送到ERP的状态         Add By Sunny.Wu 2016.09.05
     * @param order 订单
     */
    @Override
    public void updateOrderERPStatus(Order order) {
        orderMapper.updateOrderERPStatus(order);
    }
    /**
     * 更新订单申报状态               Add By Sibyl.sun 2016.09.13
     * @param order 订单
     */
    @Override
    public void updateOrderDeclaraStatus(Order order){ orderMapper.updateOrderDeclaraStatus(order);}


    /**
     * 更新威盛物流状态               Add By Sibyl.sun 2016.09.19
     * @param order 订单
     */
    @Override
    public void updateOrderExpressStatus(Order order){
        orderMapper.updateOrderExpressStatus(order);
    }

}
