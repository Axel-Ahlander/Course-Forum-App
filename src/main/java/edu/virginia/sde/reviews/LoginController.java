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

    public static User activeUser;

    public void usernameLogin() {
        passwordField.requestFocus();
    }

    public void passwordLogin() {
        loginButton.fire();
    }

    public void loginButton(ActionEvent e) throws IOException {
        if (validLogin()) {
            UserDAO dao = new UserDAO();
          //  CourseSearchController csc = new CourseSearchController();
          //  System.out.println("THIS: " + dao.findByName(usernameTextField.getText()));
            setActiveUser(dao.findByName(usernameTextField.getText()));

       //     System.out.println("USer: " + csc.getActiveUser().getName() + activeUser.getName());


            Parent root = FXMLLoader.load(getClass().getResource("CourseSearch.fxml"));
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Course Search");
            stage.setScene(scene);
            stage.show();
            stage.centerOnScreen();
        }
    }

    public void createNewAccountClick(ActionEvent e) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("CreateAccount.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Create Account");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();
    }

    public void quitClick() {
        Platform.exit();
    }

    private boolean validLogin() {
        errorLabel.setText("");

        String username = usernameTextField.getText();
        String password = passwordField.getText();
        if (username.isEmpty()) {
            errorLabel.setText("Error: no username entered. Enter a registered username or create a new account");
            return false;
        }
        return usernamePasswordMatches(username, password);
    }

    private boolean usernamePasswordMatches(String username, String password) {
        UserDAO dao = new UserDAO();
        User user = dao.findByName(username);
        if (user != null && user.getPassword().equals(password)) {
            return true;
        } else {
            if (!usernameExists(username)){
                errorLabel.setText("Error: There is no user with this username, please create a new account");
            }
            else {
                errorLabel.setText("Error: The password provided is wrong, please try again");
            }
            return false;
        }
    }

    private boolean usernameExists(String username) {
        UserDAO dao = new UserDAO();
        User user = dao.findByName(username);

        if (user != null){
            return true;
        }
        else {
            return false;
        }
    }

    //why not just use a static variable for user? prof did an instance var, and said singleton would
    //also work...
    //temporary way to keep track of the active user
    public static void setActiveUser(User user){
        activeUser = user;
    }
}
