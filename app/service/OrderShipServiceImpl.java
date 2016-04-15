package service;

import entity.order.OrderShip;
import mapper.OrderShipMapper;

import javax.inject.Inject;

/**
 * Created by tiffany on 15/12/17.
 */
public class OrderShipServiceImpl implements OrderShipService{

    @Inject
    private OrderShipMapper orderShipMapper;

    /**
     * 由订单Id获取订单收货地址
     * @param orderId
     * @return
     */
    @Override
    public OrderShip getShipByOrderId(Long orderId) {
        return orderShipMapper.getShipByOrderId(orderId);
    }
}
