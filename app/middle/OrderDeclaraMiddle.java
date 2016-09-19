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
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static play.libs.Json.newObject;

/**
 * 订单申报
 * Created by sibyl.sun on 16/9/13.
 */
public class OrderDeclaraMiddle {


    @Inject
    private OrderService orderService;

    @Inject
    private OrderSplitService orderSplitService;

    @Inject
    private OrderShipService orderShipService;

    @Inject
    private OrderLineService orderLineService;


    public void orderDeclare(Long orderId){
        Order order=orderService.getOrderById(orderId);

        if(null==order){
            Logger.error("没有订单信息orderId="+orderId);
            return ;
        }

        //时间格式
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");
        String commitTime=simpleDateFormat.format(new Date());
        ObjectNode result = newObject();
        result.put("version","v1.9"); //网关版本
        result.put("commitTime",commitTime); //提交时间
        result.put("coName",SysParCom.ORDER_DECLARA_CONAME); //企业名称
        String coCode=SysParCom.ORDER_DECLARA_COCODE;
        result.put("coCode",coCode); //企业代码
        result.put("serialNumber",coCode+commitTime+"00001"); //流水号 规则为 coCode（7 位）+YYYYMMDDHHMMSS(14 位)+5 位流水号
        result.put("merchantOrderId",order.getOrderId()); //商户订单号
        //    result.put("assBillNo","9281736002"); //物流分运单号 物流ᨀ供的唯一分运单号 可不传
        result.put("orderCommitTime",simpleDateFormat.format(order.getOrderCreateAt())); //订单提交时间


        //发件人相关信息
        result.put("senderName",SysParCom.SENDER_NAME);//发件人姓名
        result.put("senderTel",SysParCom.SENDER_TEL);// 发件人电话
        result.put("SenderCompanyName",SysParCom.SENDER_COMPANY_NAME);//发件方公司名称
        result.put("senderAddr",SysParCom.SENDER_ADDR);// 发件人地址
        result.put("senderZip","100000");//发件地邮编
        result.put("senderCity",SysParCom.SENDER_CITY);//发件地城市
        result.put("senderProvince",SysParCom.SENDER_PROVINCE);// 发件地省/州名
        result.put("senderCountry",SysParCom.SENDER_COUNTRY);//发件地国家 韩国KOR



        result.put("cargoDescript","韩国正品物品"); //订单商品信息简述 TODO ...
        //   result.put("allCargoTotalPrice",528.00);//全部购买商品合计总价
        result.put("allCargoTotalPrice",order.getPayTotal());//全部购买商品合计总价
        result.put("allCargoTotalTax",0);//全部购买商品#行邮#缴税总价
        result.put("expressPrice",0);//物流运费
        result.put("otherPrice",0);//其它费用


        //收件人信息
        OrderShip orderAddress=orderShipService.getShipByOrderId(orderId);

        if(null==orderAddress){
            Logger.error("没有订单地址信息orderId="+orderId);
            return ;
        }


        result.put("recPerson",orderAddress.getDeliveryName());// 收货人姓名
        result.put("recPhone",orderAddress.getDeliveryTel());// 收货人电话
        result.put("recCountry","中国");// 收货地国家
        result.put("recProvince", orderAddress.getDeliveryCity().split(" ")[0]);// 收货地省/州
        result.put("recCity",orderAddress.getDeliveryCity().split(" ")[2]);// 收货地城市
        result.put("recAddress",orderAddress.getDeliveryAddress());// 收货地地址
        //    result.put("recZip","100000");// 收货地邮编 ,可为空

        result.put("serverType","S01"); //业务类型 S01：一般进口 S02：保税区进口
        result.put("custCode","2244");//海关关区代码
        result.put("operationCode","1");//操作编码 1：新增
//        result.put("customDeclCo","");//物流进境申报企业,可为空
//        result.put("spt","");//扩展字段 ,可为空


        List<OrderLine> orderLineList=orderLineService.getLineByOrderId(orderId);
        if(null==orderLineList||orderLineList.size()<=0){
            Logger.error("没有商品信息orderId="+orderId);
            return ;
        }


        List<JsonNode> cargoeList=new ArrayList<>();
        for(OrderLine orderLine:orderLineList){
            ObjectNode cargoe = newObject();
            cargoe.put("cargoName",orderLine.getSkuTitle());// 单项购买商品名
            cargoe.put("cargoCode","ABCDEFG12345");// 单项购买商品编号  电商商品备案时的编号 TODO ....
//            cargoe.put("cargoNum",1);// 单项购买商品数量
//            cargoe.put("cargoUnitPrice",528.00);// 单项购买商品单价
//            cargoe.put("cargoTotalPrice",528.00);//单项购买商品总价
            cargoe.put("cargoNum",orderLine.getAmount());// 单项购买商品数量
            cargoe.put("cargoUnitPrice",orderLine.getPrice());// 单项购买商品单价
            cargoe.put("cargoTotalPrice",orderLine.getPrice().multiply(BigDecimal.valueOf(orderLine.getAmount())));//单项购买商品总价
            cargoe.put("cargoTotalTax",0);// 单项购买商品#行邮#缴税总价
            cargoeList.add(cargoe);
        }
        result.putPOJO("cargoes",cargoeList);

        //支付信息
        if("ALIPAY".equals(order.getPayMethod())){
            result.put("payMethod","ALIPAY");//支付方式
            result.put("payMerchantCode","2088811744291968"); //企业支付编号 TODO ...
        }else if("WEIXIN".equals(order.getPayMethod())){
            result.put("payMethod","WEIXIN");//支付方式 TODO ...
            if("APP".equals(order.getPayMethodSub())){
                result.put("payMerchantCode","1342936201"); //企业支付编号 TODO ...
            }else{
                result.put("payMerchantCode","1372832702"); //企业支付编号 TODO ...
            }

        }else if("JD".equals(order.getPayMethod())){
            result.put("payMethod","JD");//支付方式 TODO ...
            result.put("payMerchantCode","23237662"); //企业支付编号 TODO ...
        }else {
            Logger.error("支付方式不存在"+order.getPayMethod());
            return;
        }
        result.put("payMerchantName","北京东方爱怡斯科技有限公司"); //企业支付名称

        result.put("payAmount",order.getPayTotal()); //支付总金额
        result.put("payCUR","CNY"); //付款币种
        result.put("payID",order.getPgTradeNo()); //支付交易号
        result.put("payTime",simpleDateFormat.format(order.getUpdatedAt())); //支付交易时间

        String EData= result.toString();

        String md5Str=EData+SysParCom.ORDER_DECLARA_MD5KEY;

        Logger.info("md5加密串"+md5Str);
        String SignMsg= Crypto.md5(md5Str).toUpperCase();

        String EDataEncodeStr="";
        try {
            EDataEncodeStr= URLEncoder.encode(EData,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String sendMsg="EData="+EDataEncodeStr+"&SignMsg="+SignMsg;

        Logger.info("EDataEncodeStr="+EDataEncodeStr+",SignMsg="+SignMsg);

        Logger.info("发送内容"+sendMsg);

        RequestBody formBody = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded; charset=utf-8"),sendMsg);
        //创建一个OkHttpClient对象
        OkHttpClient client = new OkHttpClient();
        try {

            Request request =new Request.Builder()
                    .url(SysParCom.ORDER_DECLARA_URL).post(formBody)
                    .build();
            client.setConnectTimeout(15, TimeUnit.SECONDS);

            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String re= URLDecoder.decode(new String(response.body().bytes(),"utf-8"),"utf-8");
                //打印服务端返回结果
                JsonNode jsonNode= Json.parse(re);
                Logger.info("海关返回信息--->" + jsonNode);
                order.setDeclaraResult(re); //申报返回结果
                if(jsonNode.has("status")){
                    String status=jsonNode.get("status").asText();
                    if("SUCCESS".equalsIgnoreCase(status)){ //申报成功
                        order.setDeclaraStatus("S");
                        order.setDeclaraNo(jsonNode.get("declaraNo").asText());//申报备案号
                    }else{
                        order.setDeclaraStatus("F");//申报失败
                    }
                }
                orderService.updateOrderDeclaraStatus(order);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 计算税
     * @param price 单价
     * @param taxRate 税率  比如30%,传入的是30
     * @param vatRate 增值税率    比如17%,传入的是17
     * @param expressPrice 运费
     * @return
     */
    private BigDecimal calTax(BigDecimal price,BigDecimal taxRate,BigDecimal vatRate,BigDecimal expressPrice){
        /**
         * 税率计算公式 例如：消费税税率为30%、单价为100元的进口羽毛球，运费为0的计税公式如下：

         关税=0元

         消费税=100/(1-0.3)×0.3=42.86元   即100*30/(100-30)
         增值税=(100+42.86)×0.17=24.29元
         应缴纳的综合税=(42.85+24.29)×0.7=47.49元

         新税收政策规定，运费将计入完税价格,消费税税率为30%、单价为100元的进口羽毛球，运费为6元的计税公式如下：

         关税=0元

         消费税=(100+6)/(1-0.3)×0.3=45.43元
         增值税=(100+6+45.43)×0.17=25.74元
         综合税=(45.43+25.74)×0.7=49.82元
         */

        //消费税
        BigDecimal saleTax=BigDecimal.ZERO;
        if(taxRate.compareTo(BigDecimal.ZERO)>0){
            saleTax=(price.add(expressPrice)).multiply(taxRate).divide((new BigDecimal(100).subtract(taxRate))).setScale(2,BigDecimal.ROUND_HALF_UP);
        }
        //增值税
        BigDecimal vat=BigDecimal.ZERO;
        if(vatRate.compareTo(BigDecimal.ZERO)>0){
            vat=(price.add(expressPrice).add(saleTax)).multiply(vatRate).divide(new BigDecimal(100)).setScale(2,BigDecimal.ROUND_HALF_UP);
        }
        //综合税
        BigDecimal tax=(saleTax.add(vat)).multiply(new BigDecimal(0.7)).setScale(2,BigDecimal.ROUND_HALF_UP);

        Logger.info("price="+price+",taxRate="+taxRate+",vatRate="+vatRate+",expressPrice="+expressPrice+",消费税="+saleTax+", 增值税="+vat+",综合税="+tax);

        return  tax;

    }
}
