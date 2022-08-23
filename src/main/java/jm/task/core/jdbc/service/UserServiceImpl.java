package jm.task.core.jdbc.service;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService {
    public void createUsersTable() throws SQLException, ClassNotFoundException {
        final String sqlCreateUsertable = new String("CREATE TABLE IF NOT EXISTS user "
                + "(id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255) "
                + "NOT NULL, last_name VARCHAR(255) NOT NULL, age TINYINT "
                + "NOT NULL);");
        try {
            Util util = Util.getInstance();
            Connection connection = util.getDbconnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlCreateUsertable);
            int sqlResult = preparedStatement.executeUpdate();
        } catch (Exception se) {
            System.out.println(se.getMessage());
            se.printStackTrace();
        }
    }

    public void dropUsersTable() throws SQLException, ClassNotFoundException {
        final String sqlDeleteTable = new String("DROP TABLE IF EXISTS user;");
        try {
            Util util = Util.getInstance();
            Connection connection = util.getDbconnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlDeleteTable);
            int sqlResult = preparedStatement.executeUpdate();
        } catch (Exception se) {
            System.out.println(se.getMessage());
            se.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException, ClassNotFoundException {
        final String sqlInsertCommand = new String("INSERT INTO user "
                + "(name, last_name, age) "
                + "VALUES (?, ?, ?)");
        try {
            Util util = Util.getInstance();
            Connection connection = util.getDbconnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertCommand);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            int sqlResult = preparedStatement.executeUpdate();
        } catch (Exception se) {
            System.out.println(se.getMessage());
            se.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        final String sqlDeleteUserCommand = new String("DELETE FROM user "
                + "WHERE id=? ");
        try {
            Util util = Util.getInstance();
            Connection connection = util.getDbconnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlDeleteUserCommand);
            preparedStatement.setLong(1, id);
            int sqlResult = preparedStatement.executeUpdate();
        } catch (Exception se) {
            System.out.println(se.getMessage());
            se.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        final String sqlGetAllUser = new String("SELECT * FROM user");
        List<User> resultList = new ArrayList<>();
        try {
            Util util = Util.getInstance();
            Connection connection = util.getDbconnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlGetAllUser);
            ResultSet resultSet = preparedStatement.executeQuery();
            while( resultSet.next()) {
                User user = new User(resultSet.getString("name"),
                        resultSet.getString("last_name"),
                        resultSet.getByte("age"));
                resultList.add(user);
            }
        } catch (Exception se) {
            System.out.println(se.getMessage());
            se.printStackTrace();
        }
        return resultList;
    }

    public void cleanUsersTable() {
        final String sqlClearTable = new String("TRUNCATE user");
        try {
            Util util = Util.getInstance();
            Connection connection = util.getDbconnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlClearTable);
            int sqlResult = preparedStatement.executeUpdate();
        } catch (Exception se) {
            System.out.println(se.getMessage());
            se.printStackTrace();
        }
    }
}
