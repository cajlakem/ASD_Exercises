package fh.campus.asd.frontend.controllers;

import fh.campus.asd.backend.usermanagement.exceptions.creation.UserManagerFailedToCreateUserManagerException;
import fh.campus.asd.backend.usermanagement.exceptions.dataaccessor.UserManagerUserNotFoundException;
import fh.campus.asd.backend.usermanagement.exceptions.datamanger.UserManagerException;
import fh.campus.asd.backend.usermanagement.exceptions.datamanger.UserManagerPasswordsDifferentException;
import fh.campus.asd.backend.usermanagement.exceptions.datamanger.UserManagerSessionNotFoundException;
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

public class HomeController implements Initializable {

    public static UserManagerService userManagerIF;

    @FXML
    private Button logout;

    @FXML
    private Button changePassword;

    @FXML
    private Button OnDeleteAccount;

    @FXML
    private TextField username;

    @FXML
    private TextField sessionid;


    private static HomeController instance;

    public HomeController() {
        instance = this;
    }

    public static HomeController getInstance() {
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
            try {
                userManagerIF.logout(sessionid.getText());
            } catch (Exception e) {
                showAlert(e.getMessage());
            }
            Parent login = FXMLLoader.load(getClass().getResource("../resources/loginPage.fxml"));
            Stage stage = (Stage) ((Node) t.getSource()).getScene().getWindow();
            stage.setResizable(false);
            Scene members = new Scene(login);
            stage.setScene(members);
            stage.show();
        } catch (IOException e) {
            showAlert(e.getMessage());
        }
    }

    @FXML
    public void OnDeleteAccount(ActionEvent onDeleteAcountEvent) {
        try {
            System.out.println("Delete accout triggered " + onDeleteAcountEvent.toString());

            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType nodel = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(Alert.AlertType.WARNING,
                    "Are you sure you want to delete Account? sessId: " + LoginController.getInstance().getSessionId(),
                    yes,
                    nodel);

            alert.setTitle("Date format warning");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.orElse(nodel) == yes) {
                System.out.println("The Yes Button pressed ");
                String name = LoginController.getInstance().getUserName();
                String sesId = LoginController.getInstance().getSessionId();
                try {
                    userManagerIF.deleteUserProfile(sesId);
                    ButtonType ok = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                    alert = new Alert(Alert.AlertType.CONFIRMATION, "Successfully deleted user: " + name + "SessionId: " + sesId, ok);
                    alert.setTitle("Account Deletion");
                    alert.setHeaderText("Deleted...");
                    Optional<ButtonType> alertResponse = alert.showAndWait();
                    if (alertResponse.isPresent()) {
                        System.out.println("------Switching to the login page-----");
                        Parent membersarea = FXMLLoader.load(getClass().getResource("../resources/loginPage.fxml"));
                        Stage stage = (Stage) ((Node) onDeleteAcountEvent.getSource()).getScene().getWindow();
                        Scene members = new Scene(membersarea);
                        stage.setScene(members);
                        stage.show();
                    }

                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());

                    ButtonType ok = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
                    alert = new Alert(Alert.AlertType.ERROR, "Unable to deleted user: " + name + "SessionId: " + sesId + "Reason: " + e.getMessage(), ok);
                    alert.setTitle("Account Deletion");
                    alert.setHeaderText("Deleted...");
                    Optional<ButtonType> alertResponse = alert.showAndWait();

                }

            } else {
                System.out.println("The No Button pressed ignore operation");

            }

        } catch (Exception e) {
            showAlert(e.getMessage());
        }

    }

    @FXML
    public void changePassword(ActionEvent actionEvent) {
        try {
            try {
                Dialog<User> dialog = new Dialog<>();
                dialog.setTitle("Reset Password");
                dialog.setHeaderText("Change Your Password:");
                dialog.setResizable(true);

                Label label1 = new Label("*Old Password: ");
                Label label2 = new Label("*New Password: ");
                Label label3 = new Label("*Confirm New Password: ");

                PasswordField text1 = new PasswordField();
                PasswordField text2 = new PasswordField();
                PasswordField text3 = new PasswordField();

                GridPane grid = new GridPane();
                grid.add(label1, 1, 1);
                grid.add(text1, 2, 1);
                grid.add(label2, 1, 3);
                grid.add(text2, 2, 3);
                grid.add(label3, 1, 4);
                grid.add(text3, 2, 4);

                dialog.getDialogPane().setContent(grid);

                ButtonType buttonTypeChange = new ButtonType("Change Password", ButtonBar.ButtonData.OK_DONE);
                ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
                dialog.getDialogPane().getButtonTypes().add(buttonTypeChange);
                dialog.getDialogPane().getButtonTypes().add(buttonTypeCancel);

                dialog.setResultConverter(new Callback<ButtonType, User>() {
                    @Override
                    public User call(ButtonType b) {
                        if (b == buttonTypeChange) {
                            try {
                                if (text1.getText().equals("") || text2.getText().equals("") || text3.getText().equals("")) {
                                    throw new UserManagerException("Check your input!");
                                }
                                if (!text2.getText().equals(text3.getText())) {
                                    throw new UserManagerPasswordsDifferentException("Passwords do not match!");
                                }

                                userManagerIF.changePassword(sessionid.getText(), text1.getText(), text2.getText());
                                return new User("", "", "", "");
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
                    alert.setTitle("Reset Password");
                    alert.setHeaderText("Your password has been changed!");
                    alert.showAndWait();
                }
            } catch (Exception e) {
                showAlert(e.getMessage());
            }
        } catch (Exception e) {
            showAlert(e.getMessage());
        }
    }

    private void showAlert(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Oh nooo...");
        alert.setHeaderText(msg);
        alert.setContentText("Stop doing that!");
        Optional<ButtonType> result = alert.showAndWait();
    }


}
