package fh.campus.asd.frontend;

import fh.campus.asd.backend.usermanagement.implementation.UserManager;
import fh.campus.asd.backend.usermanagement.implementations.JavaMemoryUserManager;
import fh.campus.asd.backend.usermanagement.implementations.SimplePasswordAuthenticator;
import fh.campus.asd.backend.usermanagement.implementations.SimpleSessionManager;
import fh.campus.asd.backend.usermanagement.interfaces.SessionManagerIF;
import fh.campus.asd.backend.usermanagement.interfaces.UserManagerIF;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    private SessionManagerIF managerIF;
    private UserManagerIF userManagerIF;
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent login = FXMLLoader.load(getClass().getResource("resources/loginPage.fxml"));
        primaryStage.setTitle("ASD User Manager v.1.0");
        primaryStage.setScene(new Scene(login));
        primaryStage.show();

        this.initBackEnd();
    }


    public static void main(String[] args) {
        launch(args);
    }

    private void initBackEnd(){
        managerIF = new SimpleSessionManager(60000, 1000);
        Thread thread = new Thread((Runnable) managerIF);
        thread.start();
        userManagerIF = new UserManager(new JavaMemoryUserManager(),managerIF , new SimplePasswordAuthenticator());
    }

}
