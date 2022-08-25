package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Main app = new Main();
        UserService userService = new UserServiceImpl();
        // stage 1
        userService.createUsersTable();
        // stage 2 (add 4 users)
        userService.saveUser("Отто", "фон Штирлиц", (byte) 45);
        userService.saveUser("Robin", "Good", (byte) 25);
        userService.saveUser("Павлик", "Морозов", (byte) 45);
        userService.saveUser("Галилео", "Галилей", (byte) 50);
        List<User> userList = userService.getAllUsers();
        for(User user: userList) {
            System.out.println(user);
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
