package controller;

import DTO.employeeuser;
import Service.ServiceFactory;
import Service.custom.EmployeeService;
import Service.custom.impl.EmployeeServiceimpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import util.CRUDutil;
import util.ServiceType;

import java.sql.SQLException;

public class editemployeeemailpasswordController {

    @FXML
    private TextField txtemail;

    @FXML
    private TextField txtemail1;

    @FXML
    private PasswordField txtpassword;

    private String originalEmail;
    EmployeeService employeeService = ServiceFactory.getInstance().getServiceType(ServiceType.Employee);

    @FXML
    void searchbtnOnAction(ActionEvent event) throws SQLException {
        String searchEmail = txtemail.getText();

        if (searchEmail == null || searchEmail.trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Input");
            alert.setContentText("Please enter an email to search.");
            alert.showAndWait();
            return;
        }

        EmployeeService customerService = new EmployeeServiceimpl();
        employeeuser employeeuser = customerService.searchById(searchEmail);

        txtemail1.setText(searchEmail);
        txtpassword.setText(searchEmail);

        if (employeeuser != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("User Found");
            alert.setHeaderText("Search Result");
            alert.setContentText("User Found Successfully");
            alert.showAndWait();

            originalEmail = employeeuser.getEmail();

            txtemail1.setText(employeeuser.getEmail());
            txtpassword.setText(employeeuser.getPassword());
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Not Found");
            alert.setHeaderText("User Not Found");
            alert.setContentText("No user found with email: " + searchEmail);
            alert.showAndWait();

            txtemail1.clear();
            txtpassword.clear();
            originalEmail = null;
        }
    }


    @FXML
    void updatebtnOnAction(ActionEvent event) throws SQLException {
        if (originalEmail == null || originalEmail.isEmpty()) {
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

        Boolean result = CRUDutil.execute(sql, newEmail, newPassword, originalEmail);

        if (result) {
            showAlert("User updated successfully");
            originalEmail = newEmail;
            txtemail.clear();
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
