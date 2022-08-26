package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() throws SQLException, ClassNotFoundException {
        final String sqlCreateUsertable = new String("CREATE TABLE IF NOT EXISTS user "
                + "(id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255) "
                + "NOT NULL, last_name VARCHAR(255) NOT NULL, age TINYINT "
                + "NOT NULL);");
        Util util = Util.getInstance();
        Connection connection = util.getDbconnection();
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(sqlCreateUsertable);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException se) {
            System.out.println(se.getMessage());
            se.printStackTrace();
            connection.rollback();
        }
    }

    public void dropUsersTable() {
        final String sqlDeleteTable = "DROP TABLE IF EXISTS user;";
        try {
            Util util = Util.getInstance();
            Connection connection = util.getDbconnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlDeleteTable);
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException se) {
            System.out.println(se.getMessage());
            se.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException,
            ClassNotFoundException {
        final String sqlInsertCommand = "INSERT INTO user "
                + "(name, last_name, age) "
                + "VALUES (?, ?, ?)";
        Util util = Util.getInstance();
        Connection connection = util.getDbconnection();
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(sqlInsertCommand);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException se) {
            System.out.println(se.getMessage());
            se.printStackTrace();
            connection.rollback();
        }
    }

    public void removeUserById(long id) throws SQLException, ClassNotFoundException {
        final String sqlDeleteUserCommand = "DELETE FROM user WHERE id=? ";
        Util util = Util.getInstance();
        Connection connection = util.getDbconnection();
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(
                sqlDeleteUserCommand);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connection.commit();
        } catch (SQLException se) {
            System.out.println(se.getMessage());
            se.printStackTrace();
            connection.rollback();
        }
    }

    public List<User> getAllUsers() {
        final String sqlGetAllUser = "SELECT * FROM user";
        List<User> resultList = new ArrayList<>();
        try {
            Util util = Util.getInstance();
            Connection connection = util.getDbconnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlGetAllUser);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
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
        // final String sqlClearTable = "TRUNCATE user";
        final String sqlClearTable = "DELETE FROM user";
        try {
            Util util = Util.getInstance();
            Connection connection = util.getDbconnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlClearTable);
            preparedStatement.executeUpdate();
        } catch (Exception se) {
            System.out.println(se.getMessage());
            se.printStackTrace();
        }
    }
}
