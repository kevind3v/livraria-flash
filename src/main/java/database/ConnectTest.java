package database;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectTest {

    public static String test() {
        try {
            Connection connection = Connect.getConnectionPostgres();

            if(connection != null){
                return "CONECTOOOOUUUUUU";
            }else{
                return "NAO CONECTOOOOUUUUUU :-(";
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}
