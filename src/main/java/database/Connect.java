package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    private static String driver;
    private static String url;
    private static String user;
    private static String password;

    public static Connection getConnectionPostgres() throws ClassNotFoundException, SQLException {
        driver = "org.postgresql.Driver";
        url = "jdbc:postgresql://localhost:5432/postgres";
        user = "postgres";
        password = "postgres";
        return getConnection();
    }

    public static Connection getConnectionMysql() throws ClassNotFoundException, SQLException {
        driver = "com.mysql.jdbc.Driver";
        url = "jdbc:mysql://localhost:3306/ecomerce";
        user = "root";
        password = "";
        return getConnection();
    }

    public static Connection getConnectionH2() throws ClassNotFoundException, SQLException {
        driver = "org.h2.Driver";
        url = "jdbc:h2:~/test";
        user = "sa";
        password = "";
        return getConnection();
    }

    private static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName(driver);
        Connection conn = DriverManager.getConnection(url, user, password);

        return conn;
    }
}
