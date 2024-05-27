package edu.virginia.sde.reviews;

import jakarta.persistence.TypedQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;

import java.util.List;

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
            String hql = "SeLECT u FROM User u WHERE lower(u.name) = lower(:Username)";
            TypedQuery<User> idQuery = session.createQuery(hql, User.class);
            idQuery.setParameter("Username", name);
            // originally had getSingleResult, but this throws exception if user is not in db
            List<User> userList = idQuery.getResultList();
            if (!userList.isEmpty()) {
                return userList.get(0); // should only ever have 1 if any matches but see above for reason
            }
            return null; // no user exists
        } catch (Exception e) {
            //TODO: research this
            // is this good to print out stacktrace if user tries logging in with username that DNE?
            e.printStackTrace();
            return null;
        }
        // not a list since username should be unique and case insensitive
    }

    public ObservableList<User> getAllUsers() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM User";
            TypedQuery<User> query = session.createQuery(hql, User.class);

            // Convert the result to an ObservableList
            List<User> resultList = query.getResultList();
            return FXCollections.observableArrayList(resultList);
        } catch (Exception e) {
            e.printStackTrace();
            return FXCollections.observableArrayList(); // or return an empty ObservableList
        }
    }
}

//String connector = jdbc:sqlite:name.sqlite

//var connection = DriverManager.getConnection(connector);
//connection.setAutoCommit(false);
//var word = "Select* from State where name = ..."
// try{
    //var preparedStatement = connection.prepareStatement(word);
    //var resultset = preparedStatement.executeQuery();
//While (resultset.next()){
    //var result = resultset.getInt();
//connector.commit();
//connection.close()'




//var file = jdbc:sqlite:jgnrwogn
//var connection = DriverManager.getConnection(file);

//connection.setAutoCommit(false):
//var word = "Select* where ..."

//try {
    //preparedStatement = connection.prepareStatement(word);
    //resultlist=preparedStatement.executeQuery();
    //while (resultList.next()){
        //

    //preparedStatement.close();
    //connection.commit();
    //connection.close();




//var session = HibernateUtil.getSessionFactory.openSession();

//session.beginTransaction
//var hql = "From User";
//User = session.get(User.class, id);

//session.persist(User);

//var result = session.createQuery(hql, user.class)
//var res = result.getSingleResult();
// var reslist = result.getResultList();

//session.getTransaction.commit();
//session.close();

//
