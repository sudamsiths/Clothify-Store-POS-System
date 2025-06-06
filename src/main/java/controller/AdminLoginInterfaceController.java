package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminLoginInterfaceController {

    @FXML
    private TextField txtadminlogin;

    @FXML
    private PasswordField txtadminpassword;

    @FXML
    void adminloginOnAction(ActionEvent event) throws IOException {
        String adminemail = "clothify@sample.com";
        String adminpassword = "clothify@sheshan";

        String email = txtadminlogin.getText();
        String password = txtadminpassword.getText();

        if (adminemail.equals(email) && adminpassword.equals(password)) {
            Stage s1 = new Stage();
            s1.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AdminServiceInterface.fxml"))));
            s1.show();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setContentText("Invalid email or password");
            alert.showAndWait();
        }
    }

    public void CashieroginOnAction(ActionEvent actionEvent) throws IOException {
        Stage s1 = new Stage();
        s1.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/CashierLoginInterface.fxml"))));
        s1.show();
    }
}
