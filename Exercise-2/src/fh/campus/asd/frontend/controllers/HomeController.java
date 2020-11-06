package fh.campus.asd.frontend.controllers;

import fh.campus.asd.backend.usermanagement.exceptions.creation.UserManagerFailedToCreateUserManagerException;
import fh.campus.asd.backend.usermanagement.exceptions.datamanger.UserManagerException;
import fh.campus.asd.backend.usermanagement.exceptions.datamanger.UserManagerSessionNotFoundException;
import fh.campus.asd.backend.usermanagement.factory.UserManagerFactory;
import fh.campus.asd.backend.usermanagement.interfaces.UserManagerService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class HomeController implements Initializable{

    public static UserManagerService userManagerIF;

    @FXML
    private Button logout;

    @FXML
    private TextField username;

    @FXML
    private TextField sessionid;


    private static HomeController instance;
    public HomeController(){
        instance = this;
    }
    public static HomeController getInstance(){
        return instance;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sessionid.setText(LoginController.getInstance().getSessionId());
        username.setText(LoginController.getInstance().getUserName());
        try {
            userManagerIF = UserManagerFactory.getUserManagerIF("SimpleUserManagerImpl");
        } catch (UserManagerFailedToCreateUserManagerException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void logout(ActionEvent t) {
        try {
            try{
                userManagerIF.logout(sessionid.getText());
            }catch (Exception e){
                showAlert(e.getMessage());
            }
            Parent login = FXMLLoader.load(getClass().getResource("../resources/loginPage.fxml"));
            Stage stage = (Stage) ((Node)t.getSource()).getScene().getWindow();
            Scene members = new Scene(login);
            stage.setScene(members);
            stage.show();
        } catch (IOException e) {
            showAlert(e.getMessage());
        }
    }

    private void showAlert(String msg){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Oh nooo...");
        alert.setHeaderText(msg);
        alert.setContentText("Stop doing that!");
        Optional<ButtonType> result = alert.showAndWait();
    }
}
