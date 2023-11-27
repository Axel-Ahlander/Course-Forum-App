package edu.virginia.sde.reviews;

import org.hibernate.Session;
import org.hibernate.Transaction;

public class CourseDAO {
    void save(Course course){
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start transaction
            transaction = session.beginTransaction();
            // save the user object
            session.persist(course);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }


    void delete(Course course) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start transaction
            transaction = session.beginTransaction();
            // save the user object
            session.remove(course);
            // commit transaction
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    Course findById(int id) {

    }

    Course findBySubject() {

    }

    Course findByTitle(String name) {

    }
}
