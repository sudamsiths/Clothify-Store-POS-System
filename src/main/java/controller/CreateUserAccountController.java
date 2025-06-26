package controller;

import Service.ServiceFactory;
import Service.custom.EmployeeService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import DTO.employeeuser;
import util.ServiceType;

import java.sql.SQLException;

public class CreateUserAccountController {

    @FXML
    private TextField txtemail;

    @FXML
    private PasswordField txtpassword;

    EmployeeService employeeService = ServiceFactory.getInstance().getServiceType(ServiceType.employeeuser);
    @FXML
    void CreateAccountOnAcrion(ActionEvent event) throws SQLException {
        String email = txtemail.getText();
        String password = txtpassword.getText();

        if (email == null || password == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Input");
            alert.setContentText("Please enter both email and password.");
            alert.showAndWait();
        return;}
        if (email.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid Input");
            alert.setContentText("Email and password cannot be empty.");
            alert.showAndWait();
            return;
        }
        employeeuser employeeuser =new employeeuser(email , password);
        Boolean account = employeeService.createAccount(employeeuser);

        if (account) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("User Registered Successfully");
            alert.setContentText("User Email: " + email);
            alert.showAndWait();
            cleartext();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Registration Failed");
            alert.setContentText("Unable to create user account.");
            alert.showAndWait();
        }
    }

    public void cleartext() {
        txtemail.clear();
        txtpassword.clear();
    }
}
