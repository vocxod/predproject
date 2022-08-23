package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static Util util;
    private static Connection dbconnection;
    private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/pp1134";
    private static final String MYSQL_PASSWORD = "Pp1134Apple1976@@@###Fuck";
    private static final String MYSQL_USER = "pp1134";
    private Util() throws SQLException, ClassNotFoundException {
        refreshConnection();
    }

    public Connection refreshConnection() throws SQLException, ClassNotFoundException {
        Connection connection = DriverManager.getConnection(MYSQL_URL,
                MYSQL_USER, MYSQL_PASSWORD);
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbconnection = connection;
        return dbconnection;
    }

    public static Util getInstance() throws SQLException, ClassNotFoundException {
        if (util == null) {
            util = new Util();
        }
        return util;
    }

    public Connection getDbconnection() {
        return dbconnection;
    }

    /*
    // реализуйте настройку соеденения с БД

    // Connect to MySQL
    public static Connection getMySQLConnection() throws SQLException,
            ClassNotFoundException {
        String hostName = "localhost";

        String dbName = "pp1134";
        String userName = "pp1134";
        String password = "Pp11Apple1976@@@###Fuck";

        return getMySQLConnection(hostName, dbName, userName, password);
    }

    public static Connection getMySQLConnection(String hostName, String dbName,
                                                String userName, String password) throws SQLException,
            ClassNotFoundException {
        // Declare the class Driver for MySQL DB
        // This is necessary with Java 5 (or older)
        // Java6 (or newer) automatically find the appropriate driver.
        // If you use Java> 5, then this line is not needed.
        Class.forName("com.mysql.jdbc.Driver");

        String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;

        Connection conn = DriverManager.getConnection(connectionURL, userName,
                password);
        return conn;
    }
*/
}
