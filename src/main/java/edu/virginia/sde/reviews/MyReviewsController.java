package edu.virginia.sde.reviews;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;

public class MyReviewsController {

    @FXML
    TableColumn<Course, String> subject;
    @FXML
    TableColumn<Course, Integer> number;
    @FXML
    TableColumn<Course, Integer> rating;
    @FXML
    TableColumn<Course, String> courseReviewsPage;
    @FXML
    TableColumn<Course, String> comment;

    @FXML
    private Hyperlink backLink;
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
    ChoiceBox<Integer> ratingChoiceBox;
    @FXML
    TextArea commentTextArea;
    private Course course;
    boolean userReviewed;

    private String reviewComment;
    private int reviewRating;

    public void handleBackLinkClick(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("CourseSearch.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Course Search");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    public void initialize(Course selectedCourse) {
        userReviewed = false;//temporary  //only first time opens review page and hasn't reviewed.
        reviewLabel.setText("Add a Review");
        errorLabel.setText("");
        addReviewSuccessLabel.setText("");

        ratingChoiceBox.getItems().addAll(1, 2, 3, 4, 5);
        course = selectedCourse;
        subjectLabel.setText(selectedCourse.getSubject());
        numberLabel.setText(String.valueOf(selectedCourse.getNumber()));
        titleLabel.setText(selectedCourse.getTitle());

        CourseReviewsService courseReviewsService = new CourseReviewsService();
        float avgRating = courseReviewsService.calculateReviewAverage(selectedCourse);
        ratingLabel.setText(String.format("%.2f", avgRating));

// filter part that should find what reviews a user has

        ReviewDAO reviewDAO = new ReviewDAO();
        ObservableList<Review> userReviews = reviewDAO.findReviewsByUser();
//        reviewList = userReviews;
        userReviews = reviewDAO.findReviewsByUser(activeUser);
        tableView.getItems().clear();
        tableView.getItems().addAll(reviewList);
        tableView.setItems(FXCollections.observableList(reviewList));
        tableView.refresh();
//        ratingLabel.setText(selectedCourse.getRating());


        reviewTable();

    }



}
