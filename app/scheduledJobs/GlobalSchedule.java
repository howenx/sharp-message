package scheduledJobs;


import com.fasterxml.jackson.annotation.JsonFormat;
import play.GlobalSettings;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import scala.concurrent.duration.FiniteDuration;
import akka.actor.Cancellable;
import akka.actor.ActorRef;
import akka.actor.Props;
import play.libs.Akka;
import scheduledJobs.ScheduleActor.ThemesDestroyActor;

/**
 * Created by tiffany on 15/12/25.
 */
public class GlobalSchedule extends GlobalSettings{
        private Cancellable canceller = null;
        @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
        Timestamp nowDate = new Timestamp(new Date().getTime());

        @Override
        public void onStart(play.Application application) {
            System.out.println("BEGIN GREETING");

            super.onStart(application);

            ActorRef actor = Akka.system().actorOf(
                    Props.create(ThemesDestroyActor.class,nowDate)
            );
            canceller = Akka.system().scheduler().schedule(
                    FiniteDuration.create(2,TimeUnit.SECONDS), //schedule开始到第一次执行的时间
                    FiniteDuration.create(10,TimeUnit.SECONDS), //第二次以后执行的时间间隔
                    actor,
                    "N/A", // 向onReceive的第一个参数传值. null的情况下,job执行时抛出异常.
                    Akka.system().dispatcher(),
                    null
            );
        }
        @Override
        public void onStop(play.Application application) {
            System.out.println("END GREETING");

            if(canceller != null) {
                canceller.cancel();
            }
            super.onStop(application);
        }


}
