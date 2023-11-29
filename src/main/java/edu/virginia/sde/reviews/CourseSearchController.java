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
    private Label username, searchErrorLabel, addCourseErrorLabel;

    @FXML
    private TextField searchSubjectTextField, searchNumberTextField, searchTitleTextField, addCourseSubjectTextField, addCourseNumberTextField, addCourseTitleTextField;


    public void initialize() {
        addCourseTabButton.setOnAction(e -> selectTab());
        selectSearchTabButton.setOnAction(e -> tabPane.getSelectionModel().select(0));

        searchErrorLabel.setText("");
        addCourseErrorLabel.setText("");
    }

    private void selectTab() {
        tabPane.getTabs().stream()
                .filter(tab -> tab.getText().equals("Add Course"))
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
        addCourseErrorLabel.setText("");
        //subject-- 2-4 characters

        //number-- 4 digits exactly
        //title-- 1-50 characters

        //check if course already is in table
        if (validAddCourseInput()){
            System.out.println("Valid");
            //code to search for and display courses that fit search criteria
            //for title look for matching substrings (i.e., don't require exact titles
            //for subject and number, require exact letter matches

        /*    if(doesCourseExistAlready){ //check if course exists, and add it
                //add course
            }
            else{
                //error message about the course already being added
            }
         */


        }
    }

    private boolean validAddCourseInput(){
        String subject = addCourseSubjectTextField.getText();
        String number = addCourseNumberTextField.getText();
        String title = addCourseTitleTextField.getText();
        //subject-- 2-4 characters
        if(!validAddCourseSubject(subject)){
            return false;
        }
        if(!validAddCourseNumber(number)){
            return false;
        }
        if(!validAddCourseTitle(title)){
            return false;
        }
        //number-- 4 digits exactly
        //title-- 1-50 characters
        return true;
    }
    private boolean validAddCourseSubject(String subject){
        if(subject.isEmpty()){
            addCourseErrorLabel.setText("Must enter a course subject");
            return false;
        }
        if(!subject.matches("[a-zA-Z]+")){
            addCourseErrorLabel.setText("Subject must be letters only");
            return false;
        }
        if(subject.length() < 2 || subject.length() > 4){
            addCourseErrorLabel.setText("Subject must be 2-4 letters long");
            return false;
        }
        return true;

    }

    private boolean validAddCourseNumber(String number){
        if(number.isEmpty()){
            addCourseErrorLabel.setText("Must enter a course number");
            return false;
        }
        if(!number.matches("\\d+")){
            addCourseErrorLabel.setText("Course number must be numbers only");
            return false;
        }
        if(number.length() != 4){
            addCourseErrorLabel.setText("Course number must be exactly 4 digits");
            return false;
        }
        return true;
    }
    private boolean validAddCourseTitle(String title){
        if(title.isEmpty() || title.length() > 50){
            addCourseErrorLabel.setText("Course title must be 1-50 characters long");
            return false;
        }
        return true;
    }

    public void handleSearchButtonClick(){
        searchErrorLabel.setText("");
        if (validSearchInput()){
            System.out.println("Valid");
            //code to search for and display courses that fit search criteria
            //for title look for matching substrings (i.e., don't require exact titles
            //for subject and number, require exact letter matches

        }

    }
    private boolean validSearchInput(){
        String subject = searchSubjectTextField.getText();
        String number = searchNumberTextField.getText();
        String title = searchTitleTextField.getText();

        if(!validSearchSubject(subject)){
            System.out.println("Bad subject");
            return false;

        }
        if(!validSearchNumber(number)){
            System.out.println("Bad number");
            return false;
        }
        return validSearchTitle(title);
    }

    private boolean validSearchTitle(String title){
        if(title.isEmpty()){
            return true;
        }
        if(title.length() > 50){
            searchErrorLabel.setText("Title must be 50 characters or less");
            return false;
        }
        return true;
    }
    private boolean validSearchSubject(String subject){
        if(subject.isEmpty()){
            return true;
        }
        if(!subject.matches("[a-zA-Z]+")){
            searchErrorLabel.setText("Subject must be letters only");
            return false;
        }
        if(subject.length() > 4){
            searchErrorLabel.setText("Course subject cannot exceed 4 letters");
            return false;
        }
        return true;
    }

    private boolean validSearchNumber(String number){
        if(number.isEmpty()){
            return true;
        }
        if(!number.matches("\\d+")){
            searchErrorLabel.setText("Course number must be numbers only");
            return false;
        }
        if(number.length() > 4){
            searchErrorLabel.setText("Course number cannot exceed 4 digits");
            return false;
        }
        return true;
    }

}
