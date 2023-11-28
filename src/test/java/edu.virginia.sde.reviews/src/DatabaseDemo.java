package edu.virginia.sde.reviews.src;

import edu.virginia.sde.reviews.*;
import org.hibernate.Session;

import java.util.List;
import java.util.logging.Level;

public class DatabaseDemo {
    public static void main(String[] args) {
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);

        deleteTables(); // only include if testing same queries and objects

        addUser();
        addCourse();
        addReview();
    }
    private static void addUser() {
        UserDAO userDAO = new UserDAO();
        User s = new User();
        s.setName("Samuel");
        s.setPassword("12345");
        userDAO.save(s);
        System.out.println("added user: " + s);
    }

    private static void addCourse() {
        CourseDAO courseDAO = new CourseDAO();
        Course SDE = new Course();
        SDE.setSubject("CS");
        SDE.setNumber(3140);
        SDE.setTitle("Software Development Essentials");
        courseDAO.save(SDE);
        System.out.println("added course: " + SDE);
    }

    private static void addReview() {
        ReviewDAO reviewDAO = new ReviewDAO();
        UserDAO userDAO = new UserDAO();
        CourseDAO courseDAO = new CourseDAO();

        // access existing users
        User user = userDAO.findByName("Samuel");
        // may cause potential issues returning list???? -> changed title to return single course
        Course course = courseDAO.findByTitle("Software Development Essentials");


        Review review = new Review();
        review.setCourse(course);
        review.setUser(user);
        review.setRating(5);
        review.setComment("very applicable class");

        reviewDAO.save(review);

        System.out.println("review added: " + review);

    }

    private static void deleteTables() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            // Delete all rows from USERS, COURSES, REVIEWS tables
            session.createQuery("DELETE FROM User", null).executeUpdate();
            session.createQuery("DELETE FROM Course", null).executeUpdate();
            session.createQuery("DELETE FROM Review", null).executeUpdate();

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
