package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.inject.Inject;
import com.iwilley.b1ec2.api.ApiException;
import middle.ShopOrderMiddle;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import service.IDService;
import service.OrderLineService;
import service.OrderService;
import service.OrderShipService;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sunny Wu 16/3/9.
 * ERP订单的操作
 */
public class ShopOrderCtrl extends Controller {

    private OrderService orderService;

    private OrderShipService orderShipService;

    private OrderLineService orderLineService;

    private IDService idService;

    public static final Integer ORDER_QUERY_INTERVAL = Integer.parseInt(play.Play.application().configuration().getString("shop.order.query.interval"));
    public static final Integer ORDER_QUERY_DELAY = Integer.parseInt(play.Play.application().configuration().getString("shop.order.query.delay"));

    @Inject
    private ShopOrderMiddle shopOrderMiddle;

    @Inject
    public ShopOrderCtrl(OrderService orderService, IDService idService, OrderShipService orderShipService, OrderLineService orderLineService) {
        this.orderService = orderService;
        this.idService = idService;
        this.orderService = orderService;
        this.orderLineService = orderLineService;
    }

    /**
     * ERP推送订单
     * @return shopOrderNo 订单编号
     * @throws ApiException
     */
    public Result shopOrderPush() throws ApiException, ParseException {
        JsonNode json = request().body().asJson();
        Long orderIds[] = new Long[json.size()];
        List<String> shopOrderCodeList = new ArrayList<>();
        for(int i=0;i<json.size();i++) {
            orderIds[i] = (json.get(i)).asLong();
            //推送之前先查询,先从ERP查询该订单若已存在则不推送
            String shopOrderNo = "";
            List<Object> salesOrderList = shopOrderMiddle.salesOrderQuery(orderIds[i].toString());
            if ((null == salesOrderList) ||  salesOrderList.size()==0) {
                shopOrderNo = shopOrderMiddle.shopOrderPush(orderIds[i]);
                shopOrderCodeList.add(shopOrderNo);
            }
        }
        return ok(shopOrderCodeList.toString());
    }

    /**
     * 根据订单编号查询ERP订单信息
     * @param shopOrderNo 平台订单编号
     * @return salesOrder
     * @throws ParseException
     * @throws ApiException
     */
    public Result salesOrderQuery(Long shopOrderNo) throws ParseException, ApiException {

        return ok(Json.toJson(shopOrderMiddle.salesOrderQuery(shopOrderNo.toString())));
    }
}
