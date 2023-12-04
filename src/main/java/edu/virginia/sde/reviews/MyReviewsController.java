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
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;

import static edu.virginia.sde.reviews.LoginController.activeUser;

public class MyReviewsController {

    @FXML
    TableColumn<Review, String> subject;
    @FXML
    TableColumn<Review, Integer> number;
    @FXML
    TableColumn<Review, Integer> rating;
    @FXML
    TableColumn<Review, String> title;
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
            return new ReadOnlyObjectWrapper<>(course != null ? course.getNumber() : 0);//ChatGPT - Debugging for how I can access fields in review class and display them.
        });

        // TODO: need to wrap columns see next method where tried
        // access and populate the cells with title information
        title.setCellValueFactory(column -> {
            Course course = column.getValue().getCourse();
            return new ReadOnlyStringWrapper(course != null ? course.getTitle() : "");
        });

        // separately add hyperlink to each title
        title.setCellFactory(column -> new TableCell<>() {
            Hyperlink hyperlink = new Hyperlink();
//            Text text = new Text();

            {
                hyperlink.setOnAction(event -> {
                    Review selectedReview = getTableView().getItems().get(getIndex());
                    Course selectedCourse = selectedReview.getCourse();
                    handleHyperlinkAction(selectedCourse);
                });
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                final Text text = new Text();
                super.updateItem(item, empty);
                text.setText(item);
//                text.wrappingWidthProperty().bind(title.widthProperty());
//                setGraphic(text);

                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    text.setText(item);
                    text.wrappingWidthProperty().bind(title.widthProperty());

                    hyperlink.setText(item);
                    hyperlink.setWrapText(true);
                    setGraphic(hyperlink);

                }
            }
        });

//        title.setCellFactory(column -> {
//
//            var cell = new TableCell<Review, String>() {
//                Hyperlink hyperlink = new Hyperlink();
//                {
//                    hyperlink.setOnAction(event -> {
//                        Review selectedReview = getTableView().getItems().get(getIndex());
//                        Course selectedCourse = selectedReview.getCourse();
//                        handleHyperlinkAction(selectedCourse);
//                    });
//                }
//                final Text text = new Text();
//
//                @Override
//                protected void updateItem(String item, boolean empty) {
//                    super.updateItem(item, empty);
//                    text.setText(item);
//                    text.wrappingWidthProperty().bind(title.widthProperty());
//                    setGraphic(text);
//
//                    if (empty || item == null) {
//                        hyperlink.setText(null);
//                    } else {
//                        hyperlink.setText(item);
//                    }
//                    setGraphic(hyperlink);
//                }
//            };
//            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
//            return cell;
//        });


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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CourseReviewsEditReview.fxml"));
            Parent root = loader.load();

            CourseReviewsEditReviewController reviewsController = loader.getController();
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
