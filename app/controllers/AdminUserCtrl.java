package controllers;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.util.Timeout;
import domain.Persist;
import modules.LevelFactory;
import modules.NewScheduler;
import play.Logger;
import play.api.libs.Codecs;
import play.mvc.Controller;
import play.mvc.Result;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import javax.inject.Inject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * Created by Sunny Wu on 16/1/28.
 * kakao china.
 */
@SuppressWarnings("unchecked")
public class AdminUserCtrl extends Controller {


    @Inject
    private NewScheduler newScheduler;

    @Inject
    private LevelFactory levelFactory;

    @Inject
    private ActorSystem system;

    public static final Timeout TIMEOUT = new Timeout(100, TimeUnit.MILLISECONDS);

    public static final Integer ORDER_QUERY_INTERVAL = Integer.parseInt(play.Play.application().configuration().getString("shop.order.query.interval"));
    public static final Integer ORDER_QUERY_DELAY = Integer.parseInt(play.Play.application().configuration().getString("shop.order.query.delay"));


    /**
     * 处理系统启动时候去做第一次请求,完成对定时任务的执行
     *
     * @return string
     */
    public Result getFirstApp(String cipher) {
        if (Codecs.md5("hmm-100901".getBytes()).equals(cipher)) {
            List<Persist> persists;
            try {
                persists = levelFactory.iterator();
                if (persists != null && persists.size() > 0) {
                    Logger.info("遍历所有持久化schedule---->\n" + persists);
                    for (Persist p : persists) {

                        ActorSelection sel = system.actorSelection(p.getActorPath());
                        Future<ActorRef> fut = sel.resolveOne(TIMEOUT);
                        ActorRef ref = Await.result(fut, TIMEOUT.duration());

                        if (p.getType().equals("scheduleOnce")){
                            Long time = p.getDelay() - (new Date().getTime() - p.getCreateAt().getTime());
                            Logger.info("重启后scheduleOnce执行时间---> " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(new Date().getTime()+time)));
                            if (time > 0) {
                                newScheduler.scheduleOnce(Duration.create(time, TimeUnit.MILLISECONDS), ref, p.getMessage());
                            } else {
                                levelFactory.delete(p.getMessage());
                                system.actorSelection(p.getActorPath()).tell(p.getMessage(), ActorRef.noSender());
                            }
                        }else if (p.getType().equals("schedule")){
                            newScheduler.schedule(Duration.create(p.getInitialDelay(), TimeUnit.MILLISECONDS),Duration.create(p.getDelay(), TimeUnit.MILLISECONDS), ref, p.getMessage());
                            Logger.info("重启后schedule执行---> 每隔 " + Duration.create(p.getDelay(), TimeUnit.MILLISECONDS).toMinutes()+" 分钟执行一次");
                        }
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                return notFound("error");
            }
            return ok("success");
        } else throw new NullPointerException(cipher);
    }
}
