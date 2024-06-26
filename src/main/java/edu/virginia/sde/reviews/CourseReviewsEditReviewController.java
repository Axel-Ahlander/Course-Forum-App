package edu.virginia.sde.reviews;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import static edu.virginia.sde.reviews.LoginController.activeUser;

public class CourseReviewsEditReviewController {
    @FXML
    Button editReviewButton, deleteReviewButton;
    @FXML
    Label subjectLabel, numberLabel, ratingLabel, titleLabel, addReviewSuccessLabel, errorLabel, dateLabel;
    @FXML
    TableColumn<Review, Timestamp> dateColumn;
    @FXML
    TableColumn<Review, Integer> ratingColumn;
    @FXML
    TableColumn<Review, String> commentColumn;
    @FXML
    TableView<Review> tableView;
    @FXML
    Hyperlink backLink;
    @FXML
    TextArea commentTextArea;
    @FXML
    ChoiceBox<Integer> ratingChoiceBox;

    private Course course;
    private String reviewComment;
    private int reviewRating;

    public void initialize(Course selectedCourse){
        errorLabel.setText("");
        addReviewSuccessLabel.setText("");
        course = selectedCourse;

        subjectLabel.setText(selectedCourse.getSubject());
        numberLabel.setText(String.valueOf(selectedCourse.getNumber()));
        titleLabel.setText(selectedCourse.getTitle());

        CourseReviewsService courseReviewsService = new CourseReviewsService();
        float avgRating = courseReviewsService.calculateReviewAverage(selectedCourse);
        ratingLabel.setText(String.format("%.2f", avgRating));


        setupTableColumns();


        ReviewDAO reviewDAO = new ReviewDAO();
        ObservableList<Review> reviewList = reviewDAO.findByCourse(course);

        tableView.getItems().clear();
        tableView.getItems().addAll(reviewList);
        tableView.setItems(FXCollections.observableList(reviewList));
        tableView.refresh();
//  Person: Professor McBurney
//  Description: wrapping text in the column of a table view, lines 71-84 (from Piazza post 784)
        commentColumn.setCellFactory(column -> {
            TableCell<Review, String> cell = new TableCell<>() {
                final Text text = new Text();
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    text.setText(item);
                    text.wrappingWidthProperty().bind(commentColumn.widthProperty());
                    setGraphic(text);
                }
            };
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            return cell;
        });




        dateColumn.setCellFactory(column -> {
            TableCell<Review, Timestamp> cell = new TableCell<>() {
                final Text text = new Text();
                @Override
                protected void updateItem(Timestamp item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                        setGraphic(null);
                    }
                    else {

                        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm");
                        String formattedDate = dateFormat.format(item);
                        text.setText(formattedDate);
                        dateLabel.setText(formattedDate);
                        setGraphic(text);
                    }
                }
            };
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            return cell;
        });
        //need to access only results with same course
        Review userReview = reviewDAO.findByUserAndCourse(activeUser, selectedCourse);
        reviewComment = userReview.getComment();
        reviewRating = userReview.getRating();
        commentTextArea.setText(reviewComment);
        ratingChoiceBox.setValue(userReview.getRating());
        //dateLabel.setText(userReview.getDate().toString());
    }

    public void initialize(Course selectedCourse, String comment, int rating){
        errorLabel.setText("");
        course = selectedCourse;
        subjectLabel.setText(selectedCourse.getSubject());
        numberLabel.setText(String.valueOf(selectedCourse.getNumber()));
        titleLabel.setText(selectedCourse.getTitle());
        //dateLabel.setText(date.toString());


        CourseReviewsService courseReviewsService = new CourseReviewsService();
        float avgRating = courseReviewsService.calculateReviewAverage(selectedCourse);
        ratingLabel.setText(String.format("%.2f", avgRating));

        setupTableColumns();

        ReviewDAO reviewDAO = new ReviewDAO();
        ObservableList<Review>reviewList = reviewDAO.findByCourse(course);

        tableView.getItems().clear();
        tableView.getItems().addAll(reviewList);
        tableView.setItems(FXCollections.observableList(reviewList));
        tableView.refresh();

        commentColumn.setCellFactory(column -> {
            TableCell<Review, String> cell = new TableCell<>() {
                final Text text = new Text();
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    text.setText(item);
                    text.wrappingWidthProperty().bind(commentColumn.widthProperty());
                    setGraphic(text);
                }
            };
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            return cell;
        });

        dateColumn.setCellFactory(column -> {
            TableCell<Review, Timestamp> cell = new TableCell<>() {
                final Text text = new Text();
                @Override
                protected void updateItem(Timestamp item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                        setGraphic(null);
                    }
                    else {

                        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm");
                        String formattedDate = dateFormat.format(item);
                        text.setText(formattedDate);
                        dateLabel.setText(formattedDate);
                        setGraphic(text);
                    }
                }
            };
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            return cell;
        });
        commentTextArea.setText(comment);
        ratingChoiceBox.setValue(rating);
        //dateLabel.setText("Submitted: " + date);

        reviewComment = commentTextArea.getText();
        reviewRating = ratingChoiceBox.getValue();
    }

    private void setupTableColumns() {
        dateColumn.setCellValueFactory(new PropertyValueFactory<Review, Timestamp>("date"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<Review, Integer>("rating"));
        commentColumn.setCellValueFactory(new PropertyValueFactory<Review, String>("comment"));
    }

    public void handleBackLinkClick(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("CourseSearch.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Course Search");
        stage.setScene(scene);
        stage.show();
    }

    public void handleDeleteButton(){
        ReviewDAO reviewDAO = new ReviewDAO();
        Review userReview = reviewDAO.findByUserAndCourse(activeUser, course);
        reviewDAO.delete(userReview);
        reviewComment = "";
        ratingChoiceBox.setValue(null);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CourseReviews.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        CourseReviewsController controller = loader.getController();
        controller.initialize(course);
        Stage stage = (Stage) editReviewButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Course Reviews");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    public void handleEditButton(){
        courseReviewSceneAddReview(course);
    }

    private void courseReviewSceneAddReview(Course selectedCourse) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CourseReviews.fxml"));
            Parent root = loader.load();
            CourseReviewsController controller = loader.getController();
            controller.initialize(selectedCourse, reviewComment, reviewRating);
            Stage stage = (Stage) editReviewButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Course Reviews");
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

//Var session = HibernateUtil.getSessionFactory.openSession();
//session.beginTransaction();

// var hql = "from User"

//Query<User>query = session.createQuery(hql, user.class);
//int singleResult = query.getSingleResult();
//List<User>results = query.getResultList();

//session.getTransaction.commit();
//session.close();



//string database = "jdbc:sqlite:name"
//var connection = DriverManager.getConnection(database);

//String hql = "Select from table where names = ?"


//try {
//var statement = connection.prepareStatement(hql);
//statement.set(1, username);
//resultSet = statement.executeQuery();

// while (resultSet.next()){
// int res = resultSet.getInt();
//Statement.clsoe()

//connection.commit();
//connection.close();






//association    _________
//realization   ------D
//dependency - use  ----->
//aggregation   -----<>
//



