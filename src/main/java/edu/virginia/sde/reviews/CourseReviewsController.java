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

public class CourseReviewsController {
    @FXML
    Button submitReviewButton;
    @FXML
    Label subjectLabel, numberLabel, ratingLabel, addReviewSuccessLabel, errorLabel, titleLabel, reviewLabel;

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
    ChoiceBox<Integer> ratingChoiceBox;
    @FXML
    TextArea commentTextArea;
    private Course course;
    boolean userReviewed;

    private String reviewComment;
    private int reviewRating;
    ObservableList<Review> reviewList;
    public void initialize(Course selectedCourse) {
        userReviewed = false;//temporary
        reviewLabel.setText("Add a Review");
        errorLabel.setText("");
        addReviewSuccessLabel.setText("");

        ratingChoiceBox.getItems().addAll(1, 2, 3, 4, 5);
        course = selectedCourse;
        subjectLabel.setText(selectedCourse.getSubject());
        numberLabel.setText(String.valueOf(selectedCourse.getNumber()));
        titleLabel.setText(selectedCourse.getTitle());

        //    ratingLabel.setText(selectedCourse.getRating());
        reviewTable();

    }
    public void initialize(Course selectedCourse, String comment, int rating){
        course = selectedCourse;

        reviewLabel.setText("Edit review");
        userReviewed = true;
        errorLabel.setText("");
        addReviewSuccessLabel.setText("");

        subjectLabel.setText(selectedCourse.getSubject());
        numberLabel.setText(String.valueOf(selectedCourse.getNumber()));
        titleLabel.setText(selectedCourse.getTitle());
        ratingChoiceBox.getItems().addAll(1, 2, 3, 4, 5);
        ratingChoiceBox.setValue(rating);
        commentTextArea.setText(comment);

        //    ratingLabel.setText(selectedCourse.getRating());
        reviewTable();

        /*
                Review userReview = reviewDAO.findByCourseAndUser(selectedCourse, activeUser);
       reviewComment = userReview.getComment();
      reviewRating = userReview.getRating();
         */

    }

    private void reviewTable(){
        dateColumn.setCellValueFactory(new PropertyValueFactory<Review, LocalDate>("date"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<Review, Integer>("rating"));
        commentColumn.setCellValueFactory(new PropertyValueFactory<Review, String>("comment"));


        ReviewDAO reviewDAO = new ReviewDAO();
        reviewList = reviewDAO.findByCourse2(course);

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
    }

    public void handleBackLinkClick(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("CourseSearch.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Course Search");
        stage.setScene(scene);
        stage.show();
    }
    public void handleSubmitReviewButton() throws IOException {
            errorLabel.setText("");
            if (ratingChoiceBox.getValue() == null) {
                errorLabel.setText("Must select a rating!");
            }
            else if (!userReviewed){ //makes a new review
                Review review = new Review();
                review.setUser(activeUser);
                review.setComment(commentTextArea.getText());
                review.setRating(ratingChoiceBox.getValue());
                review.setCourse(course);

                LocalDate date = review.getDate();
                CourseReviewsService createReview = new CourseReviewsService(review);
                createReview.saveReview();

                ReviewDAO reviewDAO = new ReviewDAO();
                ObservableList<Review> updatedReviews = reviewDAO.findByCourse2(course);
                tableView.setItems(updatedReviews);
                tableView.refresh();
            //    addReviewSuccessLabel.setText("Review successfully added.");
                courseEditTransition(date);
                userReviewed = true;
            }
            else{ // edit users review
                CourseReviewsService updateReview = new CourseReviewsService();
              updateReview.updateReview(activeUser, reviewRating, reviewComment, course);
              //  updateReview.updateReview(activeUser, ratingChoiceBox.getValue(), commentTextArea.getText());
                ReviewDAO reviewDAO = new ReviewDAO();
                ObservableList<Review> updatedReviews = reviewDAO.findByCourse2(course);

                tableView.setItems(updatedReviews);
                tableView.refresh();

              //  addReviewSuccessLabel.setText("Review successfully added.");
           //     courseEditTransition(date);
            }
        }

    private void courseEditTransition(LocalDate date) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CourseReviewsEditReview.fxml"));
            Parent root = loader.load();
            CourseReviewsEditReviewController controller = loader.getController();
     //     controller.initialize(course, commentTextArea.getText(), ratingChoiceBox.getValue(), date);
            controller.initialize(course, commentTextArea.getText(), ratingChoiceBox.getValue(), date);
            controller.initialize(course);
            Stage stage = (Stage) submitReviewButton.getScene().getWindow();
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
