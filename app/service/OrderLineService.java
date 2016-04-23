package service;

import domain.order.OrderLine;

import java.util.List;

/**
 * Created by tiffany on 15/12/17.
 */
public interface OrderLineService {
    /**
     * 由订单Id获取订单商品
     * @param splitId
     * @return
     */
    List<OrderLine> getLineBySplitId(Long splitId);

    /**
     * 由订单id获取订单商品          Added By Sunny.Wu  2016.03.07
     * @param orderId 订单id
     * @return List of OrderLine
     */
    List<OrderLine> getLineByOrderId(Long orderId);
}
