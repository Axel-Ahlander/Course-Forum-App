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
import java.util.Set;

import static edu.virginia.sde.reviews.LoginController.activeUser;

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


    public void initialize() {
        Set<Review>reviews = activeUser.getReviews();

        for (Review review : reviews){
            subject.setText(review.getCourse().getSubject());
            number.setText(String.valueOf(review.getCourse().getNumber()));
            rating.setText(String.valueOf(review.getRating()));
            comment.setText(review.getComment());

        }


        CourseReviewsService courseReviewsService = new CourseReviewsService();
        float avgRating = courseReviewsService.calculateReviewAverage(selectedCourse);
        ratingLabel.setText(String.format("%.2f", avgRating));
//        ratingLabel.setText(selectedCourse.getRating());
        reviewTable();

    }

    public void handleBackLinkClick(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("CourseSearch.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Course Search");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    public Set<Review> displayReviews() {
        LoginController currUser = new LoginController();
        UserDAO dao = new UserDAO();
        Set<Review> ret = null;
        for (User user : dao.getAllUsers()) {
            if (user.getName().equalsIgnoreCase(currUser.usernameTextField.getText())) {
                Set<Review> reviews = user.getReviews();
                ret = reviews;
            }
        }
        return ret;
    }

    public void setReview(Review review){
        subject.setText(review.getCourse().getSubject());
        number.setText(String.valueOf(review.getCourse().getNumber()));
        rating.setText(String.valueOf(review.getRating()));
        comment.setText(review.getComment());
    }

    public void addReviews(){
        Set<Review>reviews = displayReviews();
        for (Review rev : reviews){
            setReview(rev);
        }
    }



}
