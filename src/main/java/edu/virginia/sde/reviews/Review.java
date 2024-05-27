package edu.virginia.sde.reviews;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Entity
@Table(name= "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //int id
    private int id;


    @Column(name = "date")
    private Timestamp date;


    @Column(name = "rating" , nullable = false)
    private int rating;


    @Column(name = "comment", nullable = false)
    private String comment;


    @ManyToOne
    @JoinColumn(name = "course", nullable = false)
    private Course course;

    @ManyToOne
    @JoinColumn(name = "User", nullable = false)
    private User user;

    public Review(int id, int rating, String comment, Course course, User user) {
        this.id = id;
        this.rating = rating;
        this.comment = comment;
        this.course = course;
        this.user = user;
        setDate();
    }

    public Review(){
        setDate();
    }

    public int getId() {
        return id;
    }

    // used in db
    public Timestamp getDate() {
        return date;
    }

    // used in GUI
    public String getFormattedDate() {
        LocalDateTime localDateTime = date.toLocalDateTime(); // required to cast using .format()
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-yyyy-dd HH:mm");
        return localDateTime.format(formatter);
    }

    public void setDate() {
        this.date = new Timestamp(System.currentTimeMillis());
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", date=" + date +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                ", course=" + course +
                ", user=" + user +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Review review = (Review) o;
        return id == review.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

}



//var connection = DriverManager.getConnection(jdbc:sqlite:jdbc.sqlite)
//var statement = "Select* from User"
//try {
    //var preparedStatement = connection.prepareStatement(statement)
    //var resultList = preparedStatement.executeQuery();
    // while(resultList.next()){
    //int result = resultList.getInt();
    //preparedStatement.close()
    //connection.commit();
    //connection.close();

//var session = HibernateUtil.getSessionFactory().openSession();

//session.beginTransaction;

//user = session.get(user.class, id)
//session.persist(user)
//String hql = "Select u from user u where lower(u.username) = :username
//Query<User> query = session.createQuery(hql, user.class);
//int singleres = query.getSingleResult();
//set<User> results = query.getResultList();

//session.getTransaction.commit();
//session.close();



//FlowPane root = new FlowPane();
//root.getChildren().addAll();
//return root;

//button.setOnAction(e -> handlebuttonpress())
//Stage stage = new Stage();
//Scene scene = new Scene(root,300,200)
//Stage.setScene(scene);
//stage.show();


//FlowPane Root = new FlowPane();
//root.getChildren.addAll(...);
//return root();

//stage.setTitle();
//Scene scene = new Scene(root, 300,200);
//Stage.setScene(scene):
//stage.show();


//TextField input;

//input.setOnKeyPressed(KeyEvent e -> handleEntered(e))
//input.setOnKeyTyped(_


//handleEntered(KeyEvent keyevent){
// if(keyevent.getCode == keyCode.ENTER){


//FlowPane pane = new FlowPane();
//pane.getChildren().addAll();
//return pane;

//stage.setTitle();
//Scene scene = new Scene(root, 300,200);
//Stage.setScene(scene);
//stage.show();


//session.beginTransaction();

//String hql = "From User"

//query<User>queries = session.createQuery(hql, User.class)

//queries.setParameter("user", user);














//session = HibernateUtil.getSessionFactory().openSession();
// session.beginTransaction();
// String hql = "From User"
// var trans = session.createQuery(hql, user.class);
// var singleRes = trans.getSingleResult();
// var resList = trans.getResultList();
//session.getTransaction.commit()'
//session.close();



//var string = "jdbc:sqlite:table.sqlite"
//var connection = DriverManager.getConnection(string);

//var statement = create table if not exist Cars(
// key INTEGER PRIMARYKEY;
// key TEXT NOT NULL;
// unique(key, key ABORT ON CONFLICT)
//)
//try {
    //var preparedStatement = connection.prepareStatement(statement);
    //var resultList = preparedStatement.executeUpdate();
    //while (resultList.next()){
    // int res = resultList.getInt();
    //statement.close();
    //connection.commit();
    //connection.close();


//var session;
// var hql = "Select u from User u where lower(u.username) = :username"
// Query<User>query = session.createQuery(hql, user.class);
//  query.setParameter("username", username)
// var single = query.getSingleResult;
// var mult = query.getResultList();
// session.getTransaction.commit();
// session.close();




// FlowPane root = new FlowPane();
// root.getChildren().addAll();
// return root;


//stage.setTile()'
// Scene scene = new Scene(root, 300, 200);
// stage.setScene(scene);
// stage.show();



