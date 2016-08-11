package middle;

import com.fasterxml.jackson.databind.JsonNode;
import com.iwilley.b1ec2.api.ApiException;
import com.iwilley.b1ec2.api.domain.ShopOrderCreateLine;
import com.iwilley.b1ec2.api.domain.ShopOrderCreatePayment;
import com.iwilley.b1ec2.api.request.ShopOrderCreateRequest;
import domain.ID;
import domain.Inventory;
import domain.WeiShengExpress;
import domain.erp.ShopOrderOperate;
import domain.order.Order;
import domain.order.OrderLine;
import domain.order.OrderShip;
import domain.order.OrderSplit;
import play.Logger;
import play.libs.Json;
import service.*;

import javax.inject.Inject;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sunny Wu on 16/3/7.
 * kakao china.
 */
public class ShopOrderMiddle {

    @Inject
    private OrderService orderService;

    @Inject
    private OrderShipService orderShipService;

    @Inject
    private OrderSplitService orderSplitService;

    @Inject
    private OrderLineService orderLineService;

    @Inject
    private IDService idService;

    @Inject
    private InventoryService inventoryService;

    @Inject
    private WeiShengExpressService weiShengExpressService;

    /**
     * ERP 推送订单(子订单)
     * @param orderId 订单id
     * @return 平台订单编号
     * @throws ApiException
     */
    public String shopOrderPush(Long orderId) throws ApiException {
        ShopOrderOperate shopOrderOperate = new ShopOrderOperate();
        ShopOrderCreateRequest request = new ShopOrderCreateRequest();
        OrderSplit orderSplit = orderSplitService.getSplitByOrderId(orderId).get(0);
        Order order = orderService.getOrderById(orderId);
        OrderShip orderShip = orderShipService.getShipByOrderId(orderId);
        List<OrderLine> orderLineList = orderLineService.getLineByOrderId(orderId);
        ID id = idService.getID(order.getUserId().intValue());
        String memberNick = id.getPhoneNum();     //用户手机号
        String payMethod = order.getPayMethod();  //支付方式
        String paymentNo = order.getPgTradeNo();  //交易流水号

        //---推送订单之前先在ERP中创建一条会员信息
//        CustomerMiddle customerMiddle = new CustomerMiddle(orderShipService);
//        customerMiddle.customerCreate(orderId);
        //----推送订单之前现在ERP中创建一条会员信息

        //订单信息
        request.shopOrderNo = orderId.toString();//平台订单号
        request.shopId = 4;                      //店铺id
        request.memberNick = memberNick;//客户名称
        request.orderStatus = 10;              //订单状态(0:草稿 10：未发货 20：已发货 30：已完结 40：已关闭 50：已取消)
        request.shopCreatedTime = order.getOrderCreateAt();//下单时间
        request.orderStatusName = "未发货";    //平台订单状态(平台订单状态描述，如已付款，等待发货等等。都为中文描述，仅备注作用)
        int clientType = order.getClientType();
        if (clientType==1 || clientType==2) request.isMobile = true;          //是否手机订单
        else if (clientType==3) request.isMobile = false;
        request.discountFee = order.getDiscount().doubleValue();//折扣金额
        request.postFee = order.getShipFee().doubleValue();//邮费
        if (null!=orderSplit.getTotalFee()) request.goodsTotal = orderSplit.getTotalFee().doubleValue();//商品总额
        if (null!=orderSplit.getTotalPayFee()) request.orderTotal = orderSplit.getTotalPayFee().doubleValue();//应付金额
//        request.receivedTotal();                                 //实际收款
        request.shopPayTime = order.getUpdatedAt();//平台付款时间
        if (null!=order.getOrderDesc()) request.sellerMemo = order.getOrderDesc(); //买家备注
        //订单明细
        request.receiverName = orderShip.getDeliveryName();    //收货人姓名
        request.receiverState = orderShip.getDeliveryCity().split(" ")[0];//收货人省份
        request.receiverCity = orderShip.getDeliveryCity().split(" ")[1];//收货人城市
        request.receiverDistrict = orderShip.getDeliveryCity().split(" ")[2];//收货人地区
        request.receiverAddress = orderShip.getDeliveryAddress();             //收货人地址
        request.receiverZip = orderShip.getDeliveryCardNum();             //收货人身份证号
        request.receiverMobile = orderShip.getDeliveryTel();                  //收货人手机

        //订单中商品是上海仓,加上威盛快递单
        String invArea = "";
        String trackingId = "";
        String wsExporessNo = "";
        invArea = inventoryService.getInventory(orderLineList.get(0).getSkuId()).getInvArea();
        Logger.error("商品的库存地是:::"+invArea);
        if ("S".equals(invArea) || "SZ".equals(invArea)) {
            WeiShengExpress weiShengExpress = weiShengExpressService.getExpress();
            if (null != weiShengExpress) {
                trackingId = weiShengExpress.getTrackingId();
                wsExporessNo = weiShengExpress.getExpressNo();
            }
            //威盛物流单号使用后更新状态
            weiShengExpressService.useExpress(weiShengExpress);
            request.userDefinedField1 = orderShip.getDeliveryCardNum() + "," + trackingId + "," + wsExporessNo;//收货人身份证号,威盛快递单号,国内快递单号
        }
        else {
            request.userDefinedField1 = orderShip.getDeliveryCardNum();//收货人身份证号
        }

        if ("JD".equals(payMethod)) payMethod = "京东支付";
        else if ("ALIPAY".equals(payMethod)) payMethod = "支付宝";
        else if ("WEIXIN".equals(payMethod)) payMethod = "微信支付";
        request.userDefinedField2 = payMethod+","+paymentNo;//支付方式+交易流水号

        //订单商品信息
        List<ShopOrderCreateLine> itemLineInfo = new ArrayList<>();
        for(OrderLine orderLine : orderLineList) {
            ShopOrderCreateLine shopOrderCreateLine = new ShopOrderCreateLine();
            shopOrderCreateLine.shopLineNo = orderLine.getLineId().toString();    //平台订单行号
            Long skuId = orderLine.getSkuId();
            Inventory inventory = inventoryService.getInventory(skuId);
            shopOrderCreateLine.outerId = inventory.getInvCode();                //外部编码
            shopOrderCreateLine.quantity = orderLine.getAmount();   //数量
            shopOrderCreateLine.price = orderLine.getPrice().doubleValue();//价格
            shopOrderCreateLine.itemName = orderLine.getSkuTitle();        //商品名称
            shopOrderCreateLine.skuName = orderLine.getSkuColor()+orderLine.getSkuSize();//规格名称
//            shopOrderCreateLine.lineUdf1 = orderLine.getItemId().toString();
//            shopOrderCreateLine.lineUdf2 = orderLine.getSkuId().toString();
            itemLineInfo.add(shopOrderCreateLine);
        }
        //付款信息
        List<ShopOrderCreatePayment> paymentLineInfo = new ArrayList<>();
        ShopOrderCreatePayment shopOrderCreatePayment = new ShopOrderCreatePayment();
        Logger.error("付款方式:"+order.getPayMethod());
        if ("JD".equals(order.getPayMethod())) shopOrderCreatePayment.paymentId = 11;//付款方式
        else if ("ALIPAY".equals(order.getPayMethod())) shopOrderCreatePayment.paymentId = 4;
        else if ("WEIXIN".equals(order.getPayMethod())) shopOrderCreatePayment.paymentId = 12;
        if (null!=orderSplit.getTotalPayFee()) shopOrderCreatePayment.paymentTotal = orderSplit.getTotalPayFee().doubleValue();//付款金额
        shopOrderCreatePayment.paymentNo = order.getPgTradeNo();//付款单号
        paymentLineInfo.add(shopOrderCreatePayment);



        request.setItemLines(itemLineInfo);
        request.setPaymentLines(paymentLineInfo);
        JsonNode jsonNode = Json.toJson(request);
        Logger.error("订单数据::::::"+jsonNode.toString());
        return shopOrderOperate.ShopOrderPush(request);     //返回平台订单编号
    }


    /**
     * 由平台订单编号查询ERP订单信息
     * @param shopOrderNo 平台订单编号
     * @return salesOrder 订单信息
     * @throws ParseException
     * @throws ApiException
     */
    public List<Object> salesOrderQuery(String shopOrderNo) throws ParseException, ApiException {
        ShopOrderOperate shopOrderOperate = new ShopOrderOperate();
        return shopOrderOperate.SalesOrderQuery(shopOrderNo);
    }


}
