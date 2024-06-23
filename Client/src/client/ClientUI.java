package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientUI extends Application {
    public static ClientController chat; // Only one instance

    public static void main(String args[]) throws Exception {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            // Load the IpPage.fxml file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/IP_page.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	
}
