package edu.virginia.sde.reviews;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class ReviewDAO {
    void save(Review review){
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start transaction
            transaction = session.beginTransaction();
            // save the user object
            session.persist(review);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }


    void delete(Review review) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start transaction
            transaction = session.beginTransaction();
            // save the user object
            session.remove(review);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }
}
