package service;

import domain.order.OrderShip;

/**
 * Created by tiffany on 15/12/17.
 */
public interface OrderShipService {
    /**
     * 由订单Id获取订单收货地址
     * @param orderId
     * @return
     */
    OrderShip getShipByOrderId(Long orderId);
}
