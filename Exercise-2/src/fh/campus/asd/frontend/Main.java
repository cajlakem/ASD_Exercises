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
    public static String guiTitle = "ASD User Manager v.1.0";
    public static String javaFxTemplatePath = "resources/loginPage.fxml";

    public void start(Stage primaryStage) throws Exception {
        Parent login = FXMLLoader.load(getClass().getResource(Main.javaFxTemplatePath));
        this.scene1 = new Scene(login);
        primaryStage.setTitle(Main.guiTitle);
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
