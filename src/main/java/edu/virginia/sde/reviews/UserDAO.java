package edu.virginia.sde.reviews;

import org.hibernate.Session;
import org.hibernate.Transaction;
public class UserDAO {
    void save(User user){
            Transaction transaction = null;
            try (Session session = HibernateUtil.getSessionFactory().openSession()) {
                // start transaction
                transaction = session.beginTransaction();
                // save the user object
                session.persist(user);
                // commit transaction
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            }
    }


    void delete(User user) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start transaction
            transaction = session.beginTransaction();
            // save the user object
            session.remove(user);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    User findById(int id) {

    }

    User findByName(String name) {

    }
}
