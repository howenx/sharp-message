package util;

import play.Configuration;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * 查询参数表中的参数项
 * Created by hao on 16/2/28.
 */
@Singleton
public class SysParCom {

    public static String REDIS_URL;
    public static String REDIS_PASSWORD;
    public static Integer REDIS_PORT;
    public static String REDIS_CHANNEL;
    public static Integer ORDER_QUERY_INTERVAL;
    public static Integer ORDER_QUERY_DELAY;

    //ERP账户信息配置
    public static String URL;
    public static String COMPANY ;
    public static String LOGIN_NAME;
    public static String PASSWORD;
    public static String SECRET;

    //订单申报
    public static String ORDER_DECLARA_URL;
    public static String ORDER_DECLARA_CONAME;
    public static String ORDER_DECLARA_COCODE;
    public static String ORDER_DECLARA_MD5KEY;


    @Inject
    public SysParCom(Configuration configuration) {

        REDIS_URL = configuration.getString("redis.host");
        REDIS_PASSWORD = configuration.getString("redis.password");
        REDIS_PORT = configuration.getInt("redis.port");
        REDIS_CHANNEL = configuration.getString("redis.channel");
        ORDER_QUERY_INTERVAL = configuration.getInt("shop.order.query.interval");
        ORDER_QUERY_DELAY = configuration.getInt("shop.order.query.delay");

        URL = configuration.getString("erp.url");
        COMPANY = configuration.getString("erp.company");
        LOGIN_NAME = configuration.getString("erp.login.name");
        PASSWORD = configuration.getString("erp.login.pwd");
        SECRET = configuration.getString("erp.secret");

        ORDER_DECLARA_URL= configuration.getString("order.declara.url");
        ORDER_DECLARA_CONAME=configuration.getString("order.declara.coname");
        ORDER_DECLARA_COCODE=configuration.getString("order.declara.cocode");
        ORDER_DECLARA_MD5KEY=configuration.getString("order.declara.md5key");
    }

}
