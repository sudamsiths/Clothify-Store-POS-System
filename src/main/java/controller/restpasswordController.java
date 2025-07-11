package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;
import util.CRUDutil;

import java.sql.SQLException;

public class restpasswordController {

    public PasswordField txtemail;
    public PasswordField txtnewpassword;


    @FXML
    void cancelOnAction(ActionEvent event) {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
    }

    @FXML
    void resetPasswordOnAction(ActionEvent event) throws SQLException {
        String email = txtemail.getText();              // current email
        String newPassword = txtnewpassword.getText();  // new password

        if (email.isEmpty() || newPassword.isEmpty()) {
            showAlert("Please fill all fields");
            return;
        }

        String sql = "UPDATE user SET password = ? WHERE email = ?";

        boolean result = CRUDutil.execute(sql, newPassword, email);

        if (result) {
            showAlert("Password updated successfully");
            txtemail.clear();
            txtnewpassword.clear();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
        } else {
            showAlert("Update failed. Email not found.");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
