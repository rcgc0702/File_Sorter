package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("MainApp.fxml"));
        primaryStage.setTitle("Application: Organization");
        primaryStage.setScene(new Scene(root, 475, 210));
        References.setStage(primaryStage);
        References.setUsername(System.getProperty("user.name"));
        boolean checkComplete = References.checkDirectory();
        primaryStage.setResizable(false);

        if (checkComplete) {
            primaryStage.show();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
