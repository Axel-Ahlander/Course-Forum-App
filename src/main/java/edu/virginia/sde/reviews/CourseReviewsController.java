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
    Label subjectLabel, numberLabel, ratingLabel, addReviewSuccessLabel, errorLabel;

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


    public void initialize(){
        errorLabel.setText("");
        addReviewSuccessLabel.setText("");
        ratingChoiceBox.getItems().addAll(1, 2, 3, 4, 5);

        //subjectLabel = (course subject)
        //numberLabel = (course label)
        //ratingLabel = (calculateRating
        dateColumn.setCellValueFactory(new PropertyValueFactory<Review, LocalDate>("date"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<Review, Integer>("rating"));
        commentColumn.setCellValueFactory(new PropertyValueFactory<Review, String>("comment"));

        ReviewDAO reviewDAO = new ReviewDAO();
        List<Review> reviewList = reviewDAO.getAllReviews();

        tableView.getItems().clear();
        tableView.getItems().addAll(reviewList);
        tableView.setItems(FXCollections.observableList(reviewList));
        tableView.refresh();

        commentColumn.setCellValueFactory(new PropertyValueFactory<>("comment"));
        commentColumn.setCellFactory(tc -> {
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
            } else {
                Review review = new Review();
                review.setUser(activeUser);
                review.setComment(commentTextArea.getText());
                review.setRating(ratingChoiceBox.getValue());
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
            }
        }

}
