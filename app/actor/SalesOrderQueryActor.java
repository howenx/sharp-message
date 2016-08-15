package actor;

import akka.actor.AbstractActor;
import akka.actor.Cancellable;
import akka.japi.pf.ReceiveBuilder;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import domain.Persist;
import domain.order.Order;
import domain.order.OrderSplit;
import middle.ShopOrderMiddle;
import modules.LevelFactory;
import play.Configuration;
import play.Logger;
import play.libs.Json;
import service.OrderService;
import service.OrderSplitService;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 由子订单号查询ERP订单信息的Actor
 * Created by Sunny Wu on 16/3/9.
 * kakao china.
 */
@SuppressWarnings("unchecked")
public class SalesOrderQueryActor extends AbstractActor {

    @Inject
    private LevelFactory levelFactory;

    @Inject
    private OrderService orderService;

    @Inject
    private OrderSplitService orderSplitService;

    @Inject
    Configuration configuration;

    @Inject
    public SalesOrderQueryActor(ShopOrderMiddle shopOrderMiddle) {
        receive(ReceiveBuilder.match(Long.class, orderId -> {
            Logger.error("在ERP中查询订单 "+orderId);
            Map<String,String> expressCodeMap = new ObjectMapper().convertValue(configuration.getObject("expressCode"),HashMap.class);
            JsonNode jsonNode = null;
            OrderSplit orderSplit = orderSplitService.getSplitByOrderId(orderId).get(0);
            List<Object> salesOrderList = shopOrderMiddle.salesOrderQuery(orderId.toString());
            if ((null != salesOrderList) &&  salesOrderList.size()>0) {
                jsonNode = Json.toJson(salesOrderList.get(0));
                String expressCode = jsonNode.get("express").get("expressCode").asText();//快递编码
                //查找并修改为配置文件中对应的快递100中的快递编码
                expressCode = expressCodeMap.get(expressCode);
                String expressName = jsonNode.get("express").get("expressName").asText();//快递名称
                String expressTrackNo = jsonNode.get("expressTrackNo").asText();         //快递单号
                int orderStatus = jsonNode.get("orderStatus").intValue();  //系统订单状态
                String userDefinedField1 = jsonNode.get("userDefinedField1").asText();  //自定义字段1(推送状态)
                String userDefinedField2 = jsonNode.get("userDefinedField2").asText();  //自定义字段2(海关状态)
                String userDefinedField3 = jsonNode.get("userDefinedField3").asText();  //自定义字段3(身份信息)
                String userDefinedField4 = jsonNode.get("userDefinedField4").asText();  //自定义字段4(威盛入库状态)
                String userDefinedField5 = jsonNode.get("userDefinedField5").asText();  //自定义字段5(威盛单号)
//                String userDefinedField6 = jsonNode.get("userDefinedField6").asText();  //自定义字段6
//                String userDefinedField7 = jsonNode.get("userDefinedField7").asText();  //自定义字段7
//                String userDefinedField8 = jsonNode.get("userDefinedField8").asText();  //自定义字段8
//                String userDefinedField9 = jsonNode.get("userDefinedField9").asText();  //自定义字段9
                String userDefinedField10 = jsonNode.get("userDefinedField10").asText();  //自定义字段10
                String userDefinedField11 = jsonNode.get("userDefinedField11").asText();  //自定义字段11
//                Logger.error("订单信息:::::::"+jsonNode.toString());
                //根据子订单的报关单位判断哪个自定义字段的推送状态
                //报关成功且海关返回物流单号,更新快递单号到
                if ((orderStatus==60 && !"".equals(expressTrackNo)) || ("已推送".equals(userDefinedField1) && "货物放行".equals(userDefinedField2) && "威盛已入库".equals(userDefinedField4) && null!=userDefinedField10 && !"".equals(userDefinedField10))) {
                    //更新子订单状态,物流信息
                    //orderSplit.setState("S");//报关成功
                    orderSplit.setExpressCode(expressCode);//快递公司代码
                    orderSplit.setExpressNm(expressName);//快递公司名称
                    orderSplit.setExpressNum(expressTrackNo);//快递单号
                    orderSplitService.updateSplitOrder(orderSplit);
                    //更新订单状态已发货
                    Order order = orderService.getOrderById(orderId);
                    order.setOrderStatus("D");
                    orderService.updateOrder(order);
                    Logger.error(orderId+": 订单已发货"+", 快递公司编码:"+expressCode +", 快递公司名称: "+expressName+", 快递单号: "+expressTrackNo);
//                    Logger.error("订单....."+order.toString());
//                    Logger.error("子订单信息....."+orderSplit.toString());
                    //取消schedule
                    if (levelFactory.map.containsKey(orderId)) {
                        Persist p = levelFactory.map.get(orderId);
                        p.getCancellable().cancel();
                        levelFactory.map.remove(orderId);
                    }
                    if (levelFactory.get(orderId) != null) {
                        levelFactory.delete(orderId);
                    }
                    if (levelFactory.delMap.containsKey(orderId)) {
                        Cancellable delCancellable = levelFactory.delMap.get(orderId);
                        delCancellable.cancel();
                        levelFactory.delMap.remove(orderId);
                    }
                }
            }
            Logger.debug(orderId+":sales order query....");
        }).matchAny(s-> {
            Logger.error("ERP order query error!", s.toString());
            unhandled(s);
        }).build());
    }
}
