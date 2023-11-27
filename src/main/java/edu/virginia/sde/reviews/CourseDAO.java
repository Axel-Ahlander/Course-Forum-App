package edu.virginia.sde.reviews;

import jakarta.persistence.TypedQuery;
import org.hibernate.Session;

import java.util.List;

public class CourseDAO {
    public void save(Course course){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start transaction
            session.beginTransaction();
            // save the course object
            session.persist(course);
            // commit transaction
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(Course course) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // start transaction
            session.beginTransaction();
            // delete the course object
            session.remove(course);
            // commit transaction
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Course findById(int id) {
        Course course = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            course = session.get(Course.class, id);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return course;
    }

    public List<Course> findBySubject(String subject) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT c FROM Course c WHERE c.subject = :CourseSubject";
            TypedQuery<Course> query = session.createQuery(hql, Course.class);
            query.setParameter("CourseSubject", subject);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Course> findByNumber(int number) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT c FROM Course c WHERE c.number = :CourseNumber";
            TypedQuery<Course> query = session.createQuery(hql, Course.class);
            query.setParameter("CourseNumber", number);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Course> findByTitle(String title) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT c FROM Course c WHERE c.title = :CourseTitle";
            TypedQuery<Course> query = session.createQuery(hql, Course.class);
            query.setParameter("CourseTitle", title);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
