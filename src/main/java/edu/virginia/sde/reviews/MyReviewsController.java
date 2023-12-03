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
import java.util.Set;

import static edu.virginia.sde.reviews.LoginController.activeUser;

public class MyReviewsController {

    @FXML
    TableColumn<Review, String> subject;
    @FXML
    TableColumn<Review, Integer> number;
    @FXML
    TableColumn<Review, Integer> rating;
    @FXML
    TableColumn<Review, String> courseReviewsPage;
    @FXML
    TableColumn<Review, String> comment;

    ObservableList<Review> reviewList;

    @FXML
    private Hyperlink backLink;


    public void initialize() {
        Set<Review>reviews = activeUser.getReviews();

        for (Review review : reviews){
            subject.setText(review.getCourse().getSubject());
            number.setText(String.valueOf(review.getCourse().getNumber()));
            rating.setText(String.valueOf(review.getRating()));
            comment.setText(review.getComment());

        }

        reviewTable();

    }

    private void reviewTable(){
        subject.setCellValueFactory(new PropertyValueFactory<>("subject"));
        number.setCellValueFactory(new PropertyValueFactory<Review, Integer>("number"));
        rating.setCellValueFactory(new PropertyValueFactory<Review, Integer>("number"));
        comment.setCellValueFactory(new PropertyValueFactory<Review, String>("comment"));

        reviewList = reviewDAO.findByUser(activeUser).get;

        tableView.getItems().clear(); kk
        tableView.getItems().addAll(reviewList);
        tableView.setItems(FXCollections.observableList(reviewList));
        tableView.refresh();

        comment.setCellFactory(column -> {
            TableCell<Review, String> cell = new TableCell<>() {
                final Text text = new Text();
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    text.setText(item);
                    text.wrappingWidthProperty().bind(comment.widthProperty());
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
