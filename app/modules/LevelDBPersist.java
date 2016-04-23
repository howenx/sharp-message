package modules;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.util.Timeout;
import domain.Persist;
import play.Logger;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 用于重启后执行leveldb中的持久化对象
 * Created by howen on 16/4/15.
 */
@Singleton
public class LevelDBPersist {

    public static final Timeout TIMEOUT = new Timeout(100, TimeUnit.MILLISECONDS);

    @Inject
    public LevelDBPersist(ActorSystem system,LevelFactory levelFactory,NewScheduler newScheduler) {
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
                        Logger.info("重启后schedule执行---> 每隔 " + Duration.create(p.getDelay(), TimeUnit.MILLISECONDS).toHours()+" 小时执行一次");
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
