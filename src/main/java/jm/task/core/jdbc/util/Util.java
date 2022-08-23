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

}
