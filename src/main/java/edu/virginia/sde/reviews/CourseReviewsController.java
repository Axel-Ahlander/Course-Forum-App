package edu.virginia.sde.reviews;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class CourseReviewsController {
    @FXML
    Button submitReviewButton;
    @FXML
    Label subjectLabel, numberLabel, ratingLabel;
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
    @FXML
    Label errorLabel;

    public void initialize(){
        errorLabel.setText("");
        ratingChoiceBox.getItems().add(1);
        ratingChoiceBox.getItems().add(2);
        ratingChoiceBox.getItems().add(3);
        ratingChoiceBox.getItems().add(4);
        ratingChoiceBox.getItems().add(5);

        //subjectLabel = (course subject)
        //numberLabel = (course label)
        //ratingLabel = (
        dateColumn.setCellValueFactory(new PropertyValueFactory<Review, LocalDate>("date"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<Review, Integer>("rating"));
        commentColumn.setCellValueFactory(new PropertyValueFactory<Review, String>("comment"));
        //


//        List<Review> listOfStuff = List.of(
//                new Review(1,
//                        2,
//                        "comment",
//                        new Course(0, "sub", 3, "string"),
//                        new User()
//                )
//        );
        ReviewDAO dao = new ReviewDAO();
        List<Review> reviewList = dao.findByCourse( new Course(152, "CS", 3140, "Software Development Essentials" ));

        tableView.getItems().clear();
        tableView.getItems().addAll(reviewList);

        tableView.refresh();

    }

    public void handleBackLinkClick(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("CourseSearch.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Course Search");
        stage.setScene(scene);
        stage.show();
    }
    public void handleSubmitReviewButton(ActionEvent e) throws IOException {
        errorLabel.setText("");
        if (ratingChoiceBox.getValue() == null){
            errorLabel.setText("Must select a rating!");
        }
    }

}
