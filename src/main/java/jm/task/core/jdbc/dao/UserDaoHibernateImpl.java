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
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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
        session.beginTransaction();
        User user = new User(name, lastName, age);
        session.save(user);
        session.getTransaction().commit();
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        Session session = Util.getSessionJavaConfigFactory().openSession();
        session.beginTransaction();
        try {
            User user = session.load(jm.task.core.jdbc.model.User.class, id);
            // alternative w/o exception -> user2 will eq null
            /*
            try {
                User user2 = session.getSession().get(jm.task.core.jdbc.model.User.class, id);
                if (user2 == null) {
                    System.out.println("User2 ID=" + id + " not found");
                } else {
                    System.out.println("User2 ID=" + id + " exist.");
                }
            } catch (ObjectNotFoundException onfe) {
                System.out.println("EXCEPTION ID=" + id + " not found");
            }
            */
            // Alternative END
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
        List<User> resultList = new ArrayList<>();
        Session session = Util.getSessionJavaConfigFactory().openSession();
        // Variant 1
        /*
        resultList = session.createQuery("SELECT id, name, lastName, age "
                        + "FROM User").getResultList();
        */
        // Variant 2
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> rootEntry = criteriaQuery.from(User.class);
        CriteriaQuery<User> all = criteriaQuery.select(rootEntry);
        TypedQuery<User> allQuery = session.createQuery(all);
        resultList = allQuery.getResultList();
        session.close();
        return resultList;
    }

    @Override
    public void cleanUsersTable() {
        Session session = Util.getSessionJavaConfigFactory().openSession();
        session.beginTransaction();
        try {
            Query query = session.createQuery("DELETE User WHERE id > :minId");
            query.setParameter("minId", new Long(0));
            int result = query.executeUpdate();
            if (result > 0) {
                System.out.println(result + " users removed.");
            }
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
