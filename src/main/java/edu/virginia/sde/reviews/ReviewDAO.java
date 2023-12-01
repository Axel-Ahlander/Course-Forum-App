package edu.virginia.sde.reviews;

import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ReviewDAO {
    public void save(Review review){
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.persist(review);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete(Review review) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.remove(review);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Review findById(int id) {
        Review review = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            review = session.get(Review.class, id);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return review;
    }

    public List<Review> findByUser(User user) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT r FROM Review r WHERE r.user = :user";
            TypedQuery<Review> query = session.createQuery(hql, Review.class);
            query.setParameter("user", user);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Review> findByCourse(Course course) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT r FROM Review r WHERE r.course = :course";
            TypedQuery<Review> query = session.createQuery(hql, Review.class);
            query.setParameter("course", course);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // add findByRating? but rating will not be int?


    public List<Review> getAllReviews() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Review";
            TypedQuery<Review> query = session.createQuery(hql, Review.class);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
