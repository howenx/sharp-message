package util;

import akka.actor.ActorRef;
import ch.qos.logback.classic.spi.ILoggingEvent;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

/**
 * log工具类
 * Created by howen on 16/4/20.
 */
@Singleton
public class LogUtil {

    private static ActorRef mnsActor;

    @Inject
    public LogUtil(@Named("mnsActor") ActorRef mnsActor) {
        LogUtil.mnsActor = mnsActor;
    }

    public static void sendLog(ILoggingEvent event) {
        System.out.println("这尼玛的测试----> "+event.toString());
//        mnsActor.tell(event, ActorRef.noSender());
    }
}
