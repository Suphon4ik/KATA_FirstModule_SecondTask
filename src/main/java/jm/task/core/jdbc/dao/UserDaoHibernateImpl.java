package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        SessionFactory sessionFactory = Util.getSessionFactory();

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();

                String sql = "CREATE TABLE IF NOT EXISTS users (" +
                        "id INT NOT NULL AUTO_INCREMENT, " +
                        "first_name VARCHAR(64), " +
                        "last_name VARCHAR(64), " +
                        "age INT, " +
                        "PRIMARY KEY (id))";
                session.createSQLQuery(sql).executeUpdate();
                transaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
                if (transaction != null && transaction.isActive()) {
                    transaction.rollback();
                }
            }
        }
    }

    @Override
    public void dropUsersTable() {
        SessionFactory sessionFactory = Util.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                String sql = "DROP TABLE IF EXISTS users";
                session.createSQLQuery(sql).executeUpdate();
                transaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
                if (transaction != null && transaction.isActive()) {
                    transaction.rollback();
                }
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        SessionFactory sessionFactory = Util.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                User user = new User();
                user.setName(name);
                user.setLastName(lastName);
                user.setAge(age);
                session.save(user);
                transaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
                if (transaction != null && transaction.isActive()) {
                    transaction.rollback();
                }
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        SessionFactory sessionFactory = Util.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                User user = session.get(User.class, id);
                session.delete(user);
                transaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
                if (transaction != null && transaction.isActive()) {
                    transaction.rollback();
                }
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = null;
        SessionFactory sessionFactory = Util.getSessionFactory();
        Transaction transaction = null;

        try (Session session = sessionFactory.openSession()) {
            try {
                transaction = session.beginTransaction();
                Query <User> query = session.createQuery("from User", User.class);
                users = query.list();
                transaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
                if (transaction != null && transaction.isActive()) {
                    transaction.rollback();
                }
            }
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {
        SessionFactory sessionFactory = Util.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                String sql = "DELETE FROM users";
                session.createSQLQuery(sql).executeUpdate();
                transaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
                if (transaction != null && transaction.isActive()) {
                    transaction.rollback();
                }
            }
        }
    }
}
