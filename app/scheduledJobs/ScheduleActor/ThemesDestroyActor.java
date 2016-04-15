package scheduledJobs.ScheduleActor;


import akka.actor.UntypedActor;
import com.fasterxml.jackson.annotation.JsonFormat;
import play.Logger;
import scheduledJobs.DBConn;
import java.sql.*;


/**
 * Created by tiffany on 15/12/25.
 */

public class ThemesDestroyActor extends UntypedActor{

    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private final Timestamp currentDate;
    public ThemesDestroyActor(Timestamp currentDate) {
        this.currentDate = currentDate;
    }
    @Override
    public void onReceive(Object message) throws Exception {
        Connection connection = DBConn.getConn("style");
        String updSql = "update themes set or_destroy = true where end_at<= ?";
        int result = 0;
        if(connection != null) {
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(updSql);
                preparedStatement.setTimestamp(1,this.currentDate);
                result = preparedStatement.executeUpdate();
                DBConn.colseConn(connection,preparedStatement,null);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        Logger.error("ScheduledJob:" + result +"data was updated.");
    }
}
