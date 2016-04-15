package scheduledJobs;

import play.db.DB;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by tiffany on 15/12/29.
 */
public class DBConn {

    public static Connection getConn(String dbName){
        Connection connection = DB.getConnection(dbName);
        return connection;
    }

    public static void colseConn(Connection conn, Statement stat, ResultSet rs){
        try {
            if(rs != null){
                rs.close();
            }
            if(stat != null){
                stat.close();
            }
            if(conn != null){
                conn.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }

}
