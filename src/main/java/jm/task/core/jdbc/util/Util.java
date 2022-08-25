package jm.task.core.jdbc.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import jm.task.core.jdbc.model.User;

public class Util {
    private static Util util;
    private static Connection dbconnection;
    private static SessionFactory sessionJavaConfigFactory;

    private static final String MYSQL_URL = "jdbc:mysql://localhost:3306/pp1134";
    private static final String MYSQL_PASSWORD = "Pp1134Apple1976@@@###Fuck";
    private static final String MYSQL_USER = "pp1134";


    private Util() throws SQLException, ClassNotFoundException {
        // JDBC rise.
        refreshConnection();
        // Hibernate rise.
        sessionJavaConfigFactory = getSessionJavaConfigFactory();
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

    // Session for Hibernate style connection to DAO
    private static SessionFactory buildSessionJavaConfigFactory() {
        try {
            Configuration configuration = new Configuration();
            //Create Properties, can be read from property files too.
            Properties props = new Properties();
            props.put("hibernate.connection.driver_class", "com.mysql.cj.jdbc.Driver");
            props.put("hibernate.connection.url", MYSQL_URL);
            props.put("hibernate.connection.username", MYSQL_USER);
            props.put("hibernate.connection.password", MYSQL_PASSWORD);
            props.put("hibernate.current_session_context_class", "thread");
            configuration.setProperties(props);
            //we can set mapping file or class with annotation
            configuration.addAnnotatedClass(jm.task.core.jdbc.model.User.class);
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            System.out.println("Hibernate Java Config serviceRegistry created");
            SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            return sessionFactory;
        }
        catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionJavaConfigFactory() {
        if(sessionJavaConfigFactory == null) sessionJavaConfigFactory = buildSessionJavaConfigFactory();
        return sessionJavaConfigFactory;
    }

}
