package fh.campus.asd.frontend;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class Main extends Application {
    public static volatile boolean ExitApplication = false;
    Scene scene1;

    public void start(Stage primaryStage) throws Exception {
        Parent login = FXMLLoader.load(getClass().getResource("resources/loginPage.fxml"));
        this.scene1 = new Scene(login);
        primaryStage.setTitle("ASD User Manager v.1.0");
        primaryStage.setScene(scene1);

        primaryStage.setOnCloseRequest(event -> {
            ExitApplication = true;
            Platform.exit();
        });

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
