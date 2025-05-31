package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import util.CRUDutil;

import java.sql.ResultSet;
import java.sql.SQLException;

public class editemployeeemailpasswordController {

    @FXML
    private TextField txtemail;

    @FXML
    private TextField txtemail1;

    @FXML
    private PasswordField txtpassword;

    private String originalEmail;

    @FXML
    void searchbtnOnAction(ActionEvent event) throws SQLException {
        String searchEmail = txtemail.getText();

        if (searchEmail.isEmpty()) {
            showAlert("Please enter an email to search");
            return;
        }

        ResultSet resultSet = CRUDutil.execute("select * from user where email = ?", searchEmail);

        if (resultSet.next()) {
            String email = resultSet.getString("email");
            String password = resultSet.getString("password");

            txtemail1.setText(email);
            txtpassword.setText(password);
            originalEmail = email;

            showAlert("User found successfully");
        } else {
            txtemail1.clear();
            txtpassword.clear();
            originalEmail = null;
            showAlert("User not found");
        }
    }

    @FXML
    void updatebtnOnAction(ActionEvent event) throws SQLException {
        if (originalEmail == null) {
            showAlert("Please search for a user first");
            return;
        }

        String newEmail = txtemail1.getText();
        String newPassword = txtpassword.getText();

        if (newEmail.isEmpty() || newPassword.isEmpty()) {
            showAlert("Please fill all fields");
            return;
        }

        String sql = "UPDATE user SET email = ?, password = ? WHERE email = ?";

        boolean result = (Boolean) CRUDutil.execute(sql, newEmail, newPassword, originalEmail);

        if (result) {
            showAlert("User updated successfully");
            originalEmail = newEmail;
        } else {
            showAlert("Update failed");
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
