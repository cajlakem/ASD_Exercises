package fh.campus.asd.frontend.controllers;

import fh.campus.asd.backend.usermanagement.exceptions.creation.UserManagerFailedToCreateUserManagerException;
import fh.campus.asd.backend.usermanagement.exceptions.datamanger.UserManagerException;
import fh.campus.asd.backend.usermanagement.factory.UserManagerFactory;
import fh.campus.asd.backend.usermanagement.interfaces.UserManagerService;
import fh.campus.asd.backend.usermanagement.models.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class LoginController implements Initializable{

    public static UserManagerService userManagerIF;

    @FXML
    PasswordField password;
    @FXML
    TextField email;

    private String sessionId;

    private static LoginController controller;

    public LoginController(){
        controller = this;
    }

    public static LoginController getInstance(){
        return controller;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            userManagerIF = UserManagerFactory.getUserManagerIF("SimpleUserManagerImpl");
        } catch (UserManagerFailedToCreateUserManagerException e) {
            e.printStackTrace();
        }
    }


    public void login(ActionEvent t) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Fail!");
        alert.setHeaderText("You entered weird credentials!");
        alert.setContentText("We can not let you in!");
        try {
            sessionId = userManagerIF.login(password.getText(), email.getText());
            Parent membersarea = FXMLLoader.load(getClass().getResource("../resources/membersArea.fxml"));
            Stage stage = (Stage) ((Node)t.getSource()).getScene().getWindow();
            Scene members = new Scene(membersarea);
            stage.setScene(members);
            stage.show();
        } catch (UserManagerException | IOException e) {
            this.showAlert(e.getMessage());
        }
    }

    public void join(ActionEvent actionEvent) {
        Dialog<User> dialog = new Dialog<>();
        dialog.setTitle("Joining...");
        dialog.setHeaderText("...new User Creation.");
        dialog.setResizable(true);

        Label label1 = new Label("*Email: ");

        Label label2 = new Label("Last Name: ");
        Label label3 = new Label("First Name: ");
        Label label4 = new Label("*Password: ");
        Label label5 = new Label("*Confirm Password: ");

        TextField text1 = new TextField();
        TextField text2 = new TextField();
        TextField text3 = new TextField();
        PasswordField text4 = new PasswordField();
        PasswordField text5 = new PasswordField();


        GridPane grid = new GridPane();
        grid.add(label1, 1, 1);
        grid.add(text1, 2, 1);
        grid.add(label2, 1, 3);
        grid.add(text2, 2, 3);
        grid.add(label3, 1, 4);
        grid.add(text3, 2, 4);
        grid.add(label4, 1, 5);
        grid.add(text4, 2, 5);
        grid.add(label5, 1, 6);
        grid.add(text5, 2, 6);

        dialog.getDialogPane().setContent(grid);

        ButtonType buttonTypeOk = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeOk);
        dialog.getDialogPane().getButtonTypes().add(buttonTypeCancel);

        dialog.setResultConverter(new Callback<ButtonType, User>() {
            @Override
            public User call(ButtonType b) {
                if (b == buttonTypeOk) {
                    try {
                        if((text1.getText().equals("") || text4.getText().equals("") || (!(text4.getText().equals(text4.getText()))))){
                            throw new UserManagerException("Check your input!");
                        }
                        userManagerIF.createUserProfile(text3.getText(), text2.getText(), text1.getText(), text4.getText());
                        return new User("","","","");
                    } catch (UserManagerException e) {
                        showAlert(e.getMessage());
                    }
                    return null;
                }

                return null;
            }
        });
        Optional<User> result = dialog.showAndWait();
        if (result.isPresent()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("User Creation");
            alert.setHeaderText("User has been created!");
            alert.showAndWait();
        }
    }

    private void showAlert(String msg){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Oh nooo...");
        alert.setHeaderText(msg);
        alert.setContentText("Stop doing that!");
        Optional<ButtonType> result = alert.showAndWait();
    }

    public String getUserName(){
        return email.getText();
    }

    public String getSessionId() {
        return sessionId;
    }
}
