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
import edu.virginia.sde.reviews.HibernateUtil;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.io.IOException;
import java.util.List;

public class LoginController {
    @FXML
    TextField usernameTextField;
    @FXML
    PasswordField passwordField;
    @FXML
    Button loginButton;
    @FXML
    Label errorLabel;


    public void usernameLogin() {
        passwordField.requestFocus();
    }

    public void passwordLogin() {
        loginButton.fire();
    }

    public void loginButton(ActionEvent e) throws IOException {
        if (validLogin()) {
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


    /*
    private boolean validInput(){
        String username = usernameTextField.getText();
        String password = passwordField.getText();

    return usernamePasswordMatches(username, password);
    }
*/
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
}
