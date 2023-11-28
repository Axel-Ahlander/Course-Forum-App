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
    private Button addCourseTabButton, selectSearchTabButton;

    @FXML
    private Label Username;

    @FXML
    private TextField searchSubjectTextField, searchNumberTextField, searchTitleTextField;


    @FXML
    Label errorLabel;

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

    public void myReviewsButtonClick(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MyReviews.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Log in");
        stage.setScene(scene);
        stage.show();
    }

    public void logOutAccountClick(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Log in");
        stage.setScene(scene);
        stage.show();
    }

    public void handleAddCourseButtonClick(){

    }

    public void handleSearchButtonClick(){
        if (validCourseInput()){
            System.out.println("Valid");
            //code to search for and display courses that fit search criteria
            //for title look for matching substrings (i.e., don't require exact titles
            //for subject and number, require exact letter matches

        }

    }

    private boolean validCourseInput(){
        String subject = searchSubjectTextField.getText();
        String number = searchNumberTextField.getText();
        String title = searchTitleTextField.getText();

        //subject-- 2-4 letters, store in database as uppercase
        if(!validSubject(subject)){
            System.out.println("Bad subject");
            return false;

        }
        //
        if(!validNumber(number)){
            System.out.println("Bad number");
            return false;
        }
    /*    if(title){
            System.out.println("Bad number");
            return false;
        }*/
        //number 4 digits
        return true;
    }

    private boolean validSubject(String subject){
        //errorLabel.setText("");
        if(subject.isEmpty()){
            return true;
        }
        if(!subject.matches("[a-zA-Z]+")){
            return false;
        }
        if(subject.length() < 2 || subject.length() > 4){
            return false;
        }
        //
        return true;
    }

    private boolean validNumber(String number){
        if(number.isEmpty()){
            return true;
        }
        if(!number.matches("\\d+")){
            return false;
        }
        if(number.length() < 2 || number.length() > 4){
            return false;
        }
        return true;
    }
}
