package edu.virginia.sde.reviews;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
public class LoginController {
    @FXML
    TextField usernameTextField;
    @FXML
    PasswordField passwordField;
    @FXML
    Button loginButton;
    @FXML
    Label errorLabel;

    public void usernameLogin(){
        passwordField.requestFocus();
    }
    public void passwordLogin(){
        loginButton.fire();
    }

    public void loginButton(ActionEvent e) throws IOException {
        if(validLogin()){
          //  System.out.println("login");
            /*
            Parent root = FXMLLoader.load(getClass().getResource("CourseReviews1.fxml")); // ("CourseSearch.fxml"));
            Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Course Search Screen");
            stage.setScene(scene);
            stage.show();*/
        }
    }

    public void createNewAccountClick(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("CreateAccount.fxml"));
        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Create Account");
        stage.setScene(scene);
        stage.show();
    }

    public void quitClick(){
        Platform.exit();
    }

    private boolean validLogin(){
        errorLabel.setText("");

        String username = usernameTextField.getText();
        String password = passwordField.getText();
        if (username.isEmpty()) {
            errorLabel.setText("Error: no username entered. Enter a registered username or create a new account");
            return false;
        }
        return usernamePasswordMatches(username, password);
    }

    private boolean usernamePasswordMatches(String username, String password){
        if (usernameExists(username)){
            if (true){//database.getID.contains(usernameTextField.getText()) && database.getID.contains(passwordField.getText()))){
                return true;
            }
        }
        errorLabel.setText("Wrong password, please try again");
        return false;
    }
    /*
    private boolean validInput(){
        String username = usernameTextField.getText();
        String password = passwordField.getText();

    return usernamePasswordMatches(username, password);


    }
*/
    private boolean usernameExists(String username){
        if (false){ //!database.contains(username) {
            errorLabel.setText("The username you provided doesn't exist, please create a new account");
        }
        return true; //database.userNameExists(usernameTextField.getText());
    }

}
