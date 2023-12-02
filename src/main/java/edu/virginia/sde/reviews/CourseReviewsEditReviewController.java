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
import java.time.LocalDate;
import java.util.List;

import static edu.virginia.sde.reviews.LoginController.activeUser;

public class CourseReviewsEditReviewController {
    @FXML
    Button editReviewButton;
    @FXML
    Label subjectLabel, numberLabel, ratingLabel, titleLabel, addReviewSuccessLabel, errorLabel, dateLabel;
    @FXML
    TableColumn<Review, LocalDate> dateColumn;
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
        errorLabel.setText("");//delete?
        addReviewSuccessLabel.setText("");
        course = selectedCourse;

        subjectLabel.setText(selectedCourse.getSubject());
        numberLabel.setText(String.valueOf(selectedCourse.getNumber()));
        titleLabel.setText(selectedCourse.getTitle());
        //    ratingLabel.setText(selectedCourse.getRating());

        dateColumn.setCellValueFactory(new PropertyValueFactory<Review, LocalDate>("date"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<Review, Integer>("rating"));
        commentColumn.setCellValueFactory(new PropertyValueFactory<Review, String>("comment"));

        ReviewDAO reviewDAO = new ReviewDAO();
        ObservableList<Review> reviewList = reviewDAO.findByCourse2(course);



//        Review userReview = reviewDAO.findByCourseAndUser(selectedCourse, activeUser);
//        reviewComment = userReview.getComment();
//        reviewRating = userReview.getRating();

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

        //need to access only results with same course
        Review userReview = reviewDAO.findByUserAndCourse(activeUser, selectedCourse);


        reviewComment = userReview.getComment();
        System.out.println("reviewComment1: " + reviewComment);
        System.out.println("reviewComment2: " + commentTextArea.getText());

        reviewRating = userReview.getRating();
        commentTextArea.setText(reviewComment);
        ratingChoiceBox.setValue(userReview.getRating());
        dateLabel.setText(userReview.getDate().toString());

    //    userReview = reviewDAO.findByUserAndCourse(activeUser, selectedCourse);
    }

    public void initialize(Course selectedCourse, String comment, int rating, LocalDate date){
        errorLabel.setText("");
   //     addReviewSuccessLabel.setText("");
        course = selectedCourse;
        subjectLabel.setText(selectedCourse.getSubject());
        numberLabel.setText(String.valueOf(selectedCourse.getNumber()));
        titleLabel.setText(selectedCourse.getTitle());
        dateLabel.setText(date.toString());
        //    ratingLabel.setText(selectedCourse.getRating());

        dateColumn.setCellValueFactory(new PropertyValueFactory<Review, LocalDate>("date"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<Review, Integer>("rating"));
        commentColumn.setCellValueFactory(new PropertyValueFactory<Review, String>("comment"));

        ReviewDAO reviewDAO = new ReviewDAO();
        ObservableList<Review>reviewList = reviewDAO.findByCourse2(course);

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
        commentTextArea.setText(comment);
        ratingChoiceBox.setValue(rating);
        dateLabel.setText("Submitted: " + date);

        reviewComment = commentTextArea.getText();
        reviewRating = ratingChoiceBox.getValue();

    }

    public void handleBackLinkClick(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("CourseSearch.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Course Search");
        stage.setScene(scene);
        stage.show();
    }

    public void handleEditButton(){
        courseReviewSceneAddReview(course);
    }
    /*
    public void handleEditButton() throws IOException {
       // errorLabel.setText("");
        System.out.println("HSDSFDS");
        courseReviewSceneAddReview(course);
        //way to change add a review label to edit review
        //pre set rating selection
        //make it so textfield has text in it already

     //   int ratingSelection = ratingChoiceBox.getText();
      //  courseReviewSceneAddReview(course, comment, ratingSelection);
        /*
            Review review = new Review();
            review.setUser(activeUser);
            review.setComment(commentTextArea.getText());
      //      review.setRating(ratingChoiceBox.getValue());
            //need to update this so it's set to the right course
            CourseDAO courseDAO = new CourseDAO();
            ObservableList<Course> courseList = courseDAO.getAllCourses();
            review.setCourse(courseList.get(1));
            //^

            CourseReviewsService createReview = new CourseReviewsService(review);
            createReview.saveReview();

            ReviewDAO reviewDAO = new ReviewDAO();
            // Refresh the TableView with the updated reviews
            ObservableList<Review> updatedReviews = reviewDAO.getAllReviews();
            tableView.setItems(updatedReviews);
            tableView.refresh();
            addReviewSuccessLabel.setText("Review successfully added.");

         */
    //    }

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



