package edu.virginia.sde.reviews;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
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
    TableView<Review> tableView;
    @FXML
    private Hyperlink backLink;


    public void initialize() {
        reviewList = displayReviews();
        reviewTable();

    }



    private void reviewTable(){
        rating.setCellValueFactory(new PropertyValueFactory<>("rating"));
        comment.setCellValueFactory(new PropertyValueFactory<>("comment"));

        subject.setCellValueFactory(column -> {
            Course course = column.getValue().getCourse();
            return new ReadOnlyStringWrapper(course != null ? course.getSubject() : ""); //ChatGPT - Debugging for how I can access fields in review class and display them.
        });

        number.setCellValueFactory(column -> {
            Course course = column.getValue().getCourse();
            return new ReadOnlyObjectWrapper<>(course != null ? course.getNumber() : 0);
        });

        courseReviewsPage.setCellFactory(column -> new TableCell<>() {
            Hyperlink hyperlink = new Hyperlink();

            {
                hyperlink.setOnAction(event -> {
                    Review selectedReview = getTableView().getItems().get(getIndex());
                    Course selectedCourse = selectedReview.getCourse(); // Assuming Review has a getCourse method
                    handleHyperlinkAction(selectedCourse);
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    // Assuming item is the text you want to display in the hyperlink
                    hyperlink.setText(item != null ? item : "---");
                    setGraphic(hyperlink);
                }
            }
        });


        tableView.getItems().clear();
        tableView.getItems().addAll(reviewList);
        tableView.setItems(FXCollections.observableList(reviewList));
        //tableView.refresh();

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

    private void handleHyperlinkAction(Course selectedCourse) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CourseReviews.fxml"));
            Parent root = loader.load();

            CourseReviewsController reviewsController = loader.getController();
            reviewsController.initialize(selectedCourse);

            Scene scene = new Scene(root);

            Stage stage = (Stage) tableView.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public ObservableList<Review> displayReviews() {
        ReviewDAO dao = new ReviewDAO();
        ObservableList<Review>reviews = dao.findReviewsByUser(activeUser);
        return reviews;
    }




}
