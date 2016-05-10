package middle;

import com.iwilley.b1ec2.api.ApiException;
import com.iwilley.b1ec2.api.request.CustomerCreateRequest;
import domain.erp.CustomerOperate;
import domain.order.OrderShip;
import service.*;

import javax.inject.Inject;

/**
 * Created by Sunny Wu on 16/5/10.
 * kakao china.
 */
public class CustomerMiddle {

    @Inject
    private OrderShipService orderShipService;

    public CustomerMiddle(OrderShipService orderShipService) {
        this.orderShipService = orderShipService;
    }

    /**
     * 创建ERP会员
     * @param orderId 订单id
     * @return CustomerCode
     * @throws ApiException
     */
    public String customerCreate(Long orderId) throws ApiException {
        CustomerOperate customerOperate = new CustomerOperate();
        CustomerCreateRequest request = new CustomerCreateRequest();
        request.customerName = orderId.toString();  //客户名称
        request.shopId = 4;                         //店铺Id
        OrderShip orderShip = orderShipService.getShipByOrderId(orderId);
        request.mobile =  orderShip.getDeliveryTel();//手机
        request.receiverName = orderShip.getDeliveryName();//收货人姓名
        request.province = orderShip.getDeliveryCity().split(" ")[0];//收货人省份
        request.city = orderShip.getDeliveryCity().split(" ")[1];//收货人城市
        request.district = orderShip.getDeliveryCity().split(" ")[2];//收货人地区
        request.address = orderShip.getDeliveryAddress();             //收货人地址
        request.zipCode = orderShip.getDeliveryCardNum();              //收货人邮编(用身份证号代替)
        request.userDefinedField1 = orderShip.getDeliveryCardNum();    //自定义字段1(收货人身份证号)
        return customerOperate.customerCreate(request);
    }
}
