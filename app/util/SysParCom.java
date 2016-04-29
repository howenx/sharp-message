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

    @Inject
    public SysParCom(Configuration configuration) {

        REDIS_URL = configuration.getString("redis.host");
        REDIS_PASSWORD = configuration.getString("redis.password");
        REDIS_PORT = configuration.getInt("redis.port");
        REDIS_CHANNEL = configuration.getString("redis.channel");
    }

}
