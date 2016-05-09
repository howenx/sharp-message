package controllers;

import play.mvc.Controller;


/**
 * Created by Sunny Wu on 16/1/28.
 * kakao china.
 */
public class AdminUserCtrl extends Controller {


    public static final Integer ORDER_QUERY_INTERVAL = Integer.parseInt(play.Play.application().configuration().getString("shop.order.query.interval"));
    public static final Integer ORDER_QUERY_DELAY = Integer.parseInt(play.Play.application().configuration().getString("shop.order.query.delay"));


}
