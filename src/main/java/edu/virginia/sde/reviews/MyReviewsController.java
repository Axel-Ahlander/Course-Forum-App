package edu.virginia.sde.reviews;

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

    public void setTextLabels(){

    }
}
