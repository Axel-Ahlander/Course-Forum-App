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

    public void usernameLogin(ActionEvent e){
        String username = usernameTextField.getText();
        System.out.println("Username: " + username);
        passwordField.requestFocus();
    }
    public void passwordLogin(ActionEvent e){
        String password = passwordField.getText();
        System.out.println("Password: " + password);
        loginButton.fire();
    }

    public void loginButton(ActionEvent e) throws IOException {

        if(!authenticateLogin()){
            System.out.println("Bad username");
        }
        else {
            System.out.println("login");
            /*
            Parent root = FXMLLoader.load(getClass().getResource("CourseReviews1.fxml")); // ("CourseSearch6.fxml"));
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

        /*
            var fxmlLoader = new FXMLLoader(HotDogBarGraphController.class.getResource("taco-vote.fxml"));
            var newScene = new Scene(fxmlLoader.load());
            var controller = (HotDogVotesController) fxmlLoader.getController();
            controller.setPrimaryStage(primaryStage);
            controller.setHotDogVotes(hotDogVotes);
            primaryStage.setScene(newScene);
            primaryStage.show();
         */
    }

    public void quitClick(ActionEvent e){
        Platform.exit();
    }

    //visible="false"
    private boolean authenticateLogin(){
        errorLabel.setText("");

        String username = usernameTextField.getText();
        //         String password = passwordField.getText();
        if (username.equals("")) {
            errorLabel.setText("Error: no username entered. Enter a registered username or create a new account");
            return false;
        }
        //other error handling, probably should break down into diff methods
        //no input for username or password
        //- username doesn't exist
        //password doesn't match username
        return true;
    }
}

/*
    private boolean authenticateUser(String username, String password) {
        // Add authentication logic here
        return userDatabase.containsKey(username) && userDatabase.get(username).equals(password);
    }
 */