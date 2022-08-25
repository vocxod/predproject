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

/*
package com.baeldung.daopattern.config;

import com.baeldung.daopattern.entities.User;
import com.mysql.cj.jdbc.MysqlDataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.sql.DataSource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceUnitInfo;
import org.hibernate.jpa.boot.internal.EntityManagerFactoryBuilderImpl;
import org.hibernate.jpa.boot.internal.PersistenceUnitInfoDescriptor;

public class JpaEntityManagerFactory {
    
    private final String DB_URL = "jdbc:mysql://predd";
    private final String DB_USER_NAME = "predd";
    private final String DB_PASSWORD = "bBD65855ZLzl@@@###";
    
    public EntityManager getEntityManager() {
        return getEntityManagerFactory().createEntityManager();
    }
    
    protected EntityManagerFactory getEntityManagerFactory() {
        PersistenceUnitInfo persistenceUnitInfo = getPersistenceUnitInfo(getClass().getSimpleName());
        Map<String, Object> configuration = new HashMap<>();
        return new EntityManagerFactoryBuilderImpl(new PersistenceUnitInfoDescriptor(persistenceUnitInfo), configuration)
          .build();
    }
    
    protected PersistenceUnitInfoImpl getPersistenceUnitInfo(String name) {
        return new PersistenceUnitInfoImpl(name, getEntityClassNames(), getProperties());
    }
    
    protected List<String> getEntityClassNames() {
        return Arrays.asList(getEntities())
          .stream()
          .map(Class::getName)
          .collect(Collectors.toList());
    }
    
    protected Properties getProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        properties.put("hibernate.id.new_generator_mappings", false);
        properties.put("hibernate.connection.datasource", getMysqlDataSource());
        return properties;
    }
    
    protected Class[] getEntities() {
        return new Class[]{User.class};
    }
    
    protected DataSource getMysqlDataSource() {
	MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setURL(DB_URL);
	mysqlDataSource.setUser(DB_USER_NAME);
        mysqlDataSource.setPassword(DB_PASSWORD);
	return mysqlDataSource;
    }
}


 */