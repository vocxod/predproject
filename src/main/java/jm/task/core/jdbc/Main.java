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
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    static final String DB_URL = "jdbc:mysql://localhost/pp1134";
    static final String USER = "pp1134";
    static final String PASS = "Pp1134Apple1976@@@###Fuck";

    
    public static void sqlApproach() {
        // SQL JDBC approach
        String sqlCreateUsertable = "CREATE TABLE IF NOT EXISTS user "
                + "(id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255) "
                + "NOT NULL, last_name VARCHAR(255) NOT NULL, age TINYINT "
                + "NOT NULL);";
        String[] someName = new String[]{"Анастасия", "Олег", "Петр", "София", 
            "Иван", "Светлана"};
        String[] someLastName = new String[]{"Груздева", "Попов", "Шарапова", 
            "Тополева", "Гридин"};
        String sqlInsertCommand = new String("INSERT INTO user "
                + "(name, last_name, age) "
                + "VALUES (?, ?, ?)");
        String sqlGetAllUser = new String("SELECT * FROM user");
        String sqlClearTable = "TRUNCATE user";
        String sqlDeleteUsers = "DELETE FROM user";
        String sqlDeleteTable = "DROP TABLE IF EXISTS user;";
        Random nameRander = new Random();
        Random lastNameRander = new Random();
        Random ageRander = new Random();
        try (Connection connection = DriverManager.getConnection(DB_URL, USER,
                PASS) ) {
          Class.forName("com.mysql.cj.jdbc.Driver");
          // first stage 
          PreparedStatement prepareStatement = connection.prepareStatement(
                  sqlCreateUsertable,
              Statement.RETURN_GENERATED_KEYS);
          int executeResult = prepareStatement.executeUpdate();
          System.out.println("Exec result is: " + executeResult);
          // second stage 
          for(int i = 0; i < 4; i++) {
            String name = someName[nameRander.nextInt(0, someName.length - 1)];
            String lastName = someLastName[lastNameRander.nextInt(0, 
                    someLastName.length - 1)];
            int age = ageRander.nextInt(18, 99);
            prepareStatement = connection.prepareStatement(sqlInsertCommand, 
                    Statement.RETURN_GENERATED_KEYS);
            prepareStatement.setString(1, name);
            prepareStatement.setString(2, lastName);
            prepareStatement.setInt(3, age);
            executeResult = prepareStatement.executeUpdate();
            if (executeResult == 1) {
                try ( ResultSet keys = prepareStatement.getGeneratedKeys() ) {
                    if (keys.next()) {
                        System.out.printf("Name %s Lastname %s Age %s LastInsertId"
                            + " %s\n",
                            name, lastName, age, keys.getInt(1));    
                    }                 
                }
            } else {
                // not good execution))
                System.out.print("Unknow error when insert row.");
            }            
          }
          // third stage 
          prepareStatement = connection.prepareStatement(sqlGetAllUser);
          ResultSet resultSet = prepareStatement.executeQuery();
          while(resultSet.next()) {
              int userId = resultSet.getInt("id");
              String userName = resultSet.getString("name");
              String userLastName = resultSet.getString("last_name");
              int userAge = resultSet.getInt("age");
              System.out.printf("USER: id=%s name=%s lastname=%s age=%s \n",
                      userId, userName, userLastName, userAge);
          }
          // clean table
          prepareStatement = connection.prepareStatement(sqlClearTable);
          executeResult = prepareStatement.executeUpdate();
          System.out.println("TRUNCATE PROCESS = " + executeResult);
          
          // drop table
          
          prepareStatement = connection.prepareStatement(sqlDeleteTable);
          executeResult = prepareStatement.executeUpdate();
          System.out.println("DROP USER TABLE PROCESS = " + executeResult);

        } catch (SQLException se) {
            System.out.println(se.getMessage());
            se.printStackTrace();
            Logger.getLogger(Main.class.getName()).log(Level.WARNING, null, se);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.WARNING, null, ex);
        }        
    }
    
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
    // реализуйте алгоритм здесь
    /*
     Создание таблицы User(ов)
     Добавление 4 User(ов) в таблицу с данными на свой выбор. После каждого добавления должен быть вывод в консоль ( User с именем – name добавлен в базу данных )
     Получение всех User из базы и вывод в консоль ( должен быть переопределен toString в классе User)
     Очистка таблицы User(ов)
     Удаление таблицы
    */
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
