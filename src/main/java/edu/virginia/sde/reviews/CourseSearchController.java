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

public class CourseSearchController {

    @FXML
    Hyperlink logOutLink;

    @FXML
    private TabPane tabPane;

    @FXML
    private Button addCourseTabButton;
    @FXML
    private Button selectSearchTabButton;

    public void initialize() {
        addCourseTabButton.setOnAction(e -> selectTab("Add Course"));
        selectSearchTabButton.setOnAction(e -> tabPane.getSelectionModel().select(0));
    }

    private void selectTab(String tabText) {
        tabPane.getTabs().stream()
                .filter(tab -> tab.getText().equals(tabText))
                .findFirst()
                .ifPresent(tab -> tabPane.getSelectionModel().select(tab));
    }

    public void logOutAccountClick(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Log in");
        stage.setScene(scene);
        stage.show();
    }
}
