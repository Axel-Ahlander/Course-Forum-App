package edu.virginia.sde.reviews;

import jakarta.persistence.TypedQuery;
import org.hibernate.Session;

public class UserDAO {
    public void save(User user) {
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                // start transaction
                session.beginTransaction();
                // save the user object
                session.persist(user);
                // commit transaction
                session.getTransaction().commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public void delete(User user) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.remove(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * uses default session method to get id rather than hql query
     * @param id
     * @return
     */
    public User findById(int id) {
        User user = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            user = session.get(User.class, id);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    // should search by partial name or whole?
    public User findByName(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT u FROM User u WHERE u.name = :Username";
            TypedQuery<User> idQuery = session.createQuery(hql, User.class);
            idQuery.setParameter("Username", name);
            return idQuery.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        // not a list since username should be unique and case insensitive
    }
}
