package C482;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class for the application
 */
public class Main extends Application {
    public void start(Stage stage) throws Exception {
        // Load the FXML file
        Parent parent = FXMLLoader.load(
                getClass().getResource("mainForm.fxml"));

        // Build the scene graph
        Scene scene = new Scene(parent);
        // Display window using the scene graph
        scene.getStylesheets().add(getClass().getResource("app.css").toExternalForm());
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {

        launch(args);
    }




}
