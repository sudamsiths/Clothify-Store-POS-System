package controller;

import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import util.CRUDutil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateUserAccountController {

    @FXML
    private TextField txtemail;

    @FXML
    private PasswordField txtpassword;

    @FXML
    void CreateAccountOnAcrion(ActionEvent event) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO user (email, password) VALUES (?, ?)");
        preparedStatement.setString(1, txtemail.getText());
        preparedStatement.setString(2, txtpassword.getText());

        int i = preparedStatement.executeUpdate();
        if (i > 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("User Registered Successfully");
            alert.setContentText("User Email: " + txtemail.getText());
            alert.showAndWait();
            cleartext();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Registration Failed");
            alert.setContentText("Unable to create user account.");
            alert.showAndWait();
        }

        preparedStatement.close();
    }

    public void cleartext() {
        txtemail.clear();
        txtpassword.clear();
    }
}
