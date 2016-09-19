package middle;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.squareup.okhttp.*;
import domain.order.Order;
import domain.order.OrderLine;
import domain.order.OrderShip;
import play.Logger;
import play.libs.Json;
import service.OrderLineService;
import service.OrderService;
import service.OrderShipService;
import service.OrderSplitService;
import util.Crypto;
import util.SysParCom;

import javax.inject.Inject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static play.libs.Json.newObject;
import static util.SysParCom.WEISHENG_KEY;
import static util.SysParCom.WEISHENG_ORDER_CREATE_URL;


/**
 * 威盛物流
 * Created by sibyl.sun on 16/9/18.
 */
public class WeiShengExpressMiddle {
    @Inject
    private OrderService orderService;

    @Inject
    private OrderSplitService orderSplitService;

    @Inject
    private OrderShipService orderShipService;

    @Inject
    private OrderLineService orderLineService;


    public void weiShengExpress(Long orderId) {
        Order order = orderService.getOrderById(orderId);

        if (null == order) {
            Logger.error("没有订单信息orderId=" + orderId);
            return;
        }
        ObjectNode result = newObject();

        result.put("appname", SysParCom.WEISHENG_APP_NAME);//	商户名称
        result.put("appid",SysParCom.WEISHENG_APP_ID);//	威盛快递分配给商户的ID

        //业务参数：Orders订单信息
        ObjectNode orderNode = newObject();
        orderNode.put("WarehouseCode","WELLWIN-0001");//仓库编码   TODO ...
        orderNode.put("Weight","4.3");//	重量	Number( 4,2)TODO ...
        orderNode.put("TrackingID","WSE5010243225");//威盛快递单号 TODO ...
        orderNode.put("ExpressName","JINGDONG");//	国内快递公司(代码) PICKUP - 自提  JINGDONG - 京东 YTO - 圆通 ZTO - 中通 TODO ...
        orderNode.put("ExpressNo","6666001037");//国内快递单号	自提的可以为空 TODO ...
        orderNode.put("OrderNo",orderId);//	电商订单号
//        Remark	电商备注	Varchar2(200)	Y
//        TotalPrice	订单总价	Number(8,5)	Y
//        EstimateMoney	国内物流费用金额	Number(8,5)	Y
//        PostalTax	行邮税额	Number(8,5)	Y
//        PayType	支付类型	Varchar2(20)	Y
//        PayMoney	支付总金额	Number(8,5)	Y
//        PaySerialNo 	支付流水号	Varchar2(100)	Y
//        TrackingNo	配送快递单号	Varchar2(20)	Y		配送至海外仓的快递单号
//        DataFrom	Source of the order	Varchar2(100)	Y		JINGDONG Or LOTTE
//        Name	姓名	Varchar2(20)	Y
//        CitizenID	身份证号	Varchar2(20)	Y
//        OrderOrigin	订单来源	Varchar2(20)	Y		APP WEB(默认)
//        OtherPrice	国内其他费用	Number(8,5)	Y

       // 业务数据：Shipper发货人信息
        ObjectNode shipperNode = newObject();
        shipperNode.put("SenderName",SysParCom.SENDER_NAME);//	发件人姓名
        shipperNode.put("SenderCompanyName",SysParCom.SENDER_COMPANY_NAME);//发件人公司名
        shipperNode.put("SenderCountry",SysParCom.SENDER_COUNTRY);//发件人国家代码
        shipperNode.put("SenderProvince",SysParCom.SENDER_PROVINCE);//	发件人省、州
        shipperNode.put("SenderCity",SysParCom.SENDER_CITY);//发件人城市
        shipperNode.put("SenderAddr",SysParCom.SENDER_ADDR);//	发件人地址
        shipperNode.put("SenderTel",SysParCom.SENDER_TEL);//发件人电话
        orderNode.putPOJO("Shipper",shipperNode);

    //    业务数据：Cosignee收货人信息
        //收件人信息
        OrderShip orderAddress=orderShipService.getShipByOrderId(orderId);
        if(null==orderAddress){
            Logger.error("没有订单地址信息orderId="+orderId);
            return ;
        }

        ObjectNode cosigneeNode = newObject();
        cosigneeNode.put("RecPerson",orderAddress.getDeliveryName());// 收货人姓名
        cosigneeNode.put("RecPhone",orderAddress.getDeliveryTel());// 收货人电话
        cosigneeNode.put("RecCountry","中国");// 收货地国家
        cosigneeNode.put("RecProvince", orderAddress.getDeliveryCity().split(" ")[0]);// 收货地省/州
        cosigneeNode.put("RecCity",orderAddress.getDeliveryCity().split(" ")[2]);// 收货地城市
        cosigneeNode.put("RecAddress",orderAddress.getDeliveryAddress());// 收货地地址
        orderNode.putPOJO("Cosignee",cosigneeNode);

       // 业务数据：Goods商品信息
        List<OrderLine> orderLineList=orderLineService.getLineByOrderId(orderId);
        if(null==orderLineList||orderLineList.size()<=0){
            Logger.error("没有商品信息orderId="+orderId);
            return ;
        }
        List<JsonNode> cargoeList=new ArrayList<>();
        for(OrderLine orderLine:orderLineList){
            ObjectNode cargoe = newObject();
            cargoe.put("CommodityLinkage",orderLine.getSkuId());// 商品编号
            cargoe.put("Commodity",orderLine.getSkuTitle());//商品中文名称
            cargoe.put("CommodityNum",orderLine.getAmount());// 单项购买商品数量
            cargoe.put("CommodityUnitPrice",orderLine.getPrice());// 单项购买商品单价
            cargoeList.add(cargoe);
        }
        orderNode.putPOJO("Goods",cargoeList);

        List<JsonNode> orderNodeList=new ArrayList<>();
        orderNodeList.add(orderNode);

        result.putPOJO("orders",orderNodeList);


        String EData=Json.toJson(result).toString();
        String SignMsg= Crypto.md5(EData+WEISHENG_KEY);
        String msg="";
        try {
            msg= URLEncoder.encode(EData,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        msg="EData="+msg+"&SignMsg="+SignMsg;

        Logger.info("威盛发送内容-->"+msg);

        //创建一个OkHttpClient对象
        OkHttpClient okHttpClient = new OkHttpClient();
        RequestBody formBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded; charset=utf-8"),msg);
        //创建一个请求对象
        Request request = new Request.Builder().url(WEISHENG_ORDER_CREATE_URL).post(formBody).build();
        //发送请求获取响应
        try {
            Response response=okHttpClient.newCall(request).execute();
            //判断请求是否成功
            if(response.isSuccessful()){
                //打印服务端返回结果
                JsonNode jsonNode=Json.parse(new String(response.body().bytes(), UTF_8));
                Logger.info("威盛物流返回信息--->" + jsonNode);

                //TODO ...

            }

        } catch (IOException e) {
            e.printStackTrace();
        }



    }
}
