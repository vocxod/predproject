package jm.task.core.jdbc.dao;

// import com.mysql.cj.Session;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() throws SQLException, ClassNotFoundException {
        UserDaoJDBCImpl userDaoJDBC = new UserDaoJDBCImpl();
        userDaoJDBC.createUsersTable();
    }

    @Override
    public void dropUsersTable() {
        UserDaoJDBCImpl userDaoJDBC = new UserDaoJDBCImpl();
        userDaoJDBC.dropUsersTable();
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = Util.getSessionJavaConfigFactory().openSession();
        try (session) {
            session.beginTransaction();
            User user = new User(name, lastName, age);
            session.save(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSessionJavaConfigFactory().openSession();
        session.beginTransaction();
        try {
            User user = session.load(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        } catch (ObjectNotFoundException | EntityNotFoundException e ) {
            System.out.println("User ID=" + id + " not found");
            session.getTransaction().rollback();
        }
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        List<User> resultList = new ArrayList<User>();
        Session session = Util.getSessionJavaConfigFactory().openSession();
        resultList = session.createQuery("SELECT a FROM User a", User.class).getResultList();
        session.close();
        return resultList;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getSessionJavaConfigFactory().openSession();
        session.beginTransaction();
        try {
            Query query = session.createQuery("DELETE User WHERE id > 0");
            query.executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            System.out.println("Error delete all users!");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        session.close();
    }
}
