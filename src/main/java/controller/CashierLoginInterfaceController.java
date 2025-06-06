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

public class CashierLoginInterfaceController {

    @FXML
    private TextField txtCashieremail;

    @FXML
    private PasswordField txtCashierpassword;

    @FXML
    void CashierloginOnAction(ActionEvent event) throws IOException {
        String Cashieremail = "clothifycashier@sample.com";
        String Cashierpassword = "123";

        String email = txtCashieremail.getText();
        String password = txtCashierpassword.getText();

        if (Cashieremail.equals(email) && Cashierpassword.equals(password)) {
            Stage s1 = new Stage();
            s1.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/CashierDashboardInterface.fxml"))));
            s1.show();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setContentText("Invalid email or password");
            alert.showAndWait();
        }
    }

}
