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

public class CreateAccountController {

    @FXML
    TextField usernameTextField;
    @FXML
    PasswordField passwordField;
    @FXML
    Button createAccountButton;
    @FXML
    Label errorLabel;

    public void usernameLogin(ActionEvent e){
        String username = usernameTextField.getText();
        passwordField.requestFocus();
    }
    public void passwordLogin(ActionEvent e){
        String password = passwordField.getText();
        // createAccountButton.fire();
    }

    public void createAccountButton(ActionEvent e) throws IOException {
        if (validLogin()) {
            Parent root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

    }
    public void quitClick(ActionEvent e){
        Platform.exit();
    }

    private boolean validLogin(){
        String username = usernameTextField.getText();
        String password = passwordField.getText();

        if (!isUsernameValid(username)){
            if (username == null){
                errorLabel.setText("You need to provide a username.");
            }
            else {
                errorLabel.setText("The username is already in use.");
            }
        }
        if (!isPasswordValid(password)){
            if (password.length() < 8){
                errorLabel.setText("The password is too short! You need at least 8 characters.");
            }
            else {
                errorLabel.setText("You need to provide a password.");
            }

        }
        return true;
    }
    //check that user input something
    //check that username doesn't already exist in database
    //check that password is valid-- 8 characters or more

    //then can do actual business logic or whatever database operations.. in separate class?

    private boolean isUsernameValid(String username){
        if (username != null && !username.trim().isEmpty()){
            return true;
        }
        boolean usernameExist = true; //database.userNameExists(username); //replace true with -> //Interact with database to see if username exists -> database.usernameExist(username);
        return !usernameExist;
    }

    private boolean isPasswordValid(String password){
        if (password != null && password.length() >= 8){
            return true;
        }
        else {
            return false;
        }
    }


}

