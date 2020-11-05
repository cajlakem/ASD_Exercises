package fh.campus.asd.frontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent login = FXMLLoader.load(getClass().getResource("guiConfig.fxml"));
        primaryStage.setTitle("ASD User Manager v.1.0");
        primaryStage.setScene(new Scene(login));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
