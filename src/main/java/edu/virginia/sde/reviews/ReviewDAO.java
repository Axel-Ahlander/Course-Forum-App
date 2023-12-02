package edu.virginia.sde.reviews;

import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

    public Review findByUser(User user) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT r FROM Review r WHERE r.user = :user";
            TypedQuery<Review> query = session.createQuery(hql, Review.class);
            query.setParameter("user", user);

            try {
                return query.getSingleResult();
            } catch (NoResultException e) {
                // User has no reviews
                return null;
            }
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

    public ObservableList<Review> findByCourse2(Course course) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "SELECT r FROM Review r WHERE r.course = :course";
            TypedQuery<Review> query = session.createQuery(hql, Review.class);
            query.setParameter("course", course);

            // Convert the result to an ObservableList
            List<Review> resultList = query.getResultList();
            return FXCollections.observableArrayList(resultList);
        } catch (Exception e) {
            e.printStackTrace();
            return FXCollections.observableArrayList(); // or return an empty ObservableList
        }
    }

    // add findByRating? but rating will not be int?

//
//    public List<Review> getAllReviews() {
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            String hql = "FROM Review";
//            TypedQuery<Review> query = session.createQuery(hql, Review.class);
//            return query.getResultList();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    public ObservableList<Review> getAllReviews() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Review";
            TypedQuery<Review> query = session.createQuery(hql, Review.class);

            // Convert the result to an ObservableList
            List<Review> resultList = query.getResultList();
            return FXCollections.observableArrayList(resultList);
        } catch (Exception e) {
            e.printStackTrace();
            return FXCollections.observableArrayList(); // or return an empty ObservableList
        }
    }
    public ObservableList<Review> getAllReviews(Course course) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Review r WHERE r.course = :course";
            TypedQuery<Review> query = session.createQuery(hql, Review.class);
            query.setParameter("course", course);

            List<Review> resultList = query.getResultList();
            return FXCollections.observableArrayList(resultList);
        } catch (Exception e) {
            e.printStackTrace();
            return FXCollections.observableArrayList(); // or return an empty ObservableList
        }
    }

//    public void updateReview(User user, int newRating, String newComment, Course course) {
//        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
//            session.beginTransaction();
//
//            ReviewDAO reviewDAO = new ReviewDAO(); // Pass the session to the DAO
//            Review existingReview = reviewDAO.findByCourseAndUser(course, user);
//
//            if (existingReview != null) {
//                existingReview.setRating(newRating);
//                existingReview.setComment(newComment);
//
//                // Use merge instead of persist
//                session.merge(existingReview);
//
//                session.getTransaction().commit();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


    public Review findByCourseAndUser(Course course, User user) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Review R WHERE R.course = :course AND R.user = :user";
            TypedQuery<Review> query = session.createQuery(hql, Review.class);
            query.setParameter("course", course);
            query.setParameter("user", user);

            return query.getSingleResult();
        } catch (NoResultException e) {
            return null; // User hasn't submitted a review for this course
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Review findByUserAndCourse(User user, Course course) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Review R WHERE R.user = :user AND R.course = :course";
            TypedQuery<Review> query = session.createQuery(hql, Review.class);
            query.setParameter("user", user);
            query.setParameter("course", course);

            return query.getSingleResult();
        } catch (NoResultException e) {
            return null; // Return null if no result is found
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public void update(Review review) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.merge(review); // Use merge for updating detached entities
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
