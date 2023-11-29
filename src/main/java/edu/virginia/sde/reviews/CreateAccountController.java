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
//import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateAccountController {

    @FXML
    TextField usernameTextField;
    @FXML
    TextField passwordField;
    @FXML
    Button createAccountButton;
    @FXML
    Label errorLabel;

    public void usernameLogin(){
        passwordField.requestFocus();
    }
    public void passwordLogin(){
   //     createAccountButton.fire();
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
    public void quitClick(){
        Platform.exit();
    }

    private boolean validLogin(){
        String username = usernameTextField.getText();
        String password = passwordField.getText();

        if (username.trim().isEmpty()){
            errorLabel.setText("You need to provide a username.");
            return false;
        }
        if (isUsernameTaken(username)){
            errorLabel.setText("This username is already in use.");
            return false;
        }
        if (password.trim().isEmpty()){
            errorLabel.setText("You need to provide a password.");
            return false;
        }
        if (password.length() < 8){ //password.trim().length() < 8
            errorLabel.setText("The password is too short! You need at least 8 characters.");
            return false;
        }
        return true;
    }

    private boolean isUsernameTaken(String username){
        if (username != null && !username.trim().isEmpty()){
            return false;
        }
        boolean usernameExist = true; //database.userNameExists(username); //replace true with -> //Interact with database to see if username exists -> database.usernameExist(username);
        return !usernameExist;
    }

}

