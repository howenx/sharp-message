package domain.order;

import java.io.Serializable;

/**
 * 订单收货地址
 * Created by tiffany on 15/12/14.
 */
public class OrderShip implements Serializable {
    private Long shipId;
    private Long orderId;               //订单Id
    private String deliveryName;        //收货人姓名
    private String deliveryTel;         //收货人电话
    private String deliveryCity;        //配送城市
    private String deliveryAddress;     //配送地址
    private String deliveryCardNum;     //用户身份证号

    public OrderShip() {
    }

    public OrderShip(Long shipId, Long orderId, String deliveryName, String deliveryTel, String deliveryCity, String deliveryAddress, String deliveryCardNum) {
        this.shipId = shipId;
        this.orderId = orderId;
        this.deliveryName = deliveryName;
        this.deliveryTel = deliveryTel;
        this.deliveryCity = deliveryCity;
        this.deliveryAddress = deliveryAddress;
        this.deliveryCardNum = deliveryCardNum;
    }

    @Override
    public String toString() {
        return "OrderShip{" +
                "shipId=" + shipId +
                ", orderId=" + orderId +
                ", deliveryName='" + deliveryName + '\'' +
                ", deliveryTel='" + deliveryTel + '\'' +
                ", deliveryCity='" + deliveryCity + '\'' +
                ", deliveryAddress='" + deliveryAddress + '\'' +
                ", deliveryCardNum='" + deliveryCardNum + '\'' +
                '}';
    }

    public Long getShipId() {
        return shipId;
    }

    public void setShipId(Long shipId) {
        this.shipId = shipId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getDeliveryName() {
        return deliveryName;
    }

    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
    }

    public String getDeliveryTel() {
        return deliveryTel;
    }

    public void setDeliveryTel(String deliveryTel) {
        this.deliveryTel = deliveryTel;
    }

    public String getDeliveryCity() {
        return deliveryCity;
    }

    public void setDeliveryCity(String deliveryCity) {
        this.deliveryCity = deliveryCity;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public String getDeliveryCardNum() {
        return deliveryCardNum;
    }

    public void setDeliveryCardNum(String deliveryCardNum) {
        this.deliveryCardNum = deliveryCardNum;
    }
}
