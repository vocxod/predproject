package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.dao.UserDao;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {
    UserDao userDaoJDBC = new UserDaoJDBCImpl();
    UserDao userDaoHibernate = new UserDaoHibernateImpl();

    public void createUsersTable() throws SQLException, ClassNotFoundException {
        // We will use JDBC approach instead Hibernate.
        userDaoJDBC.createUsersTable();
    }

    public void dropUsersTable() throws SQLException, ClassNotFoundException {
        // We will use JDBC approach instead Hibernate.
        userDaoJDBC.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException,
            ClassNotFoundException {
        // userDaoJDBC.saveUser(name, lastName, age);
        userDaoHibernate.saveUser(name, lastName, age);
    }

    public void removeUserById(long id) throws SQLException, ClassNotFoundException {
        // userDaoJDBC.removeUserById(id);
        userDaoHibernate.removeUserById(id);
    }

    public List<User> getAllUsers() {
        // return userDaoJDBC.getAllUsers();
        return userDaoHibernate.getAllUsers();
    }

    public void cleanUsersTable() {
        // userDaoJDBC.cleanUsersTable();
        userDaoHibernate.cleanUsersTable();
    }
}
