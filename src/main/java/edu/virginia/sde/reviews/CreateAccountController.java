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
        System.out.println("Username: " + username);
        passwordField.requestFocus();
    }
    public void passwordLogin(ActionEvent e){
        String password = passwordField.getText();
        System.out.println("Password: " + password);
        // createAccountButton.fire();
    }

    public void createAccountButton(ActionEvent e) throws IOException {
        if (validLogin()) {
            Parent root = FXMLLoader.load(getClass().getResource("LoginScene.fxml"));
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
        return true;
    }
    //check that user input something
    //check that username doesn't already exist in database
    //check that password is valid-- 8 characters or more

    //then can do actual business logic or whatever database operations.. in separate class?

}
