package edu.virginia.sde.reviews;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.logging.Level;

public class CourseReviewsApplication extends Application{
    @Override
    public void start(Stage stage) throws Exception {
        // handle logging info; SEVERE will still show us any meaningful exceptions, even when we catch and handle them
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoginScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(getClass().getResource("courseTitleColumn.css").toExternalForm());

        stage.setTitle("Log in");
        stage.setScene(scene);
        stage.show();
        stage.centerOnScreen();

    }

    public static void main(String[] args){launch(args); }

}
