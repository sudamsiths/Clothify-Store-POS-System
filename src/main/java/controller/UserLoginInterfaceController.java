package controller;

import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserLoginInterfaceController {

    @FXML
    private TextField txtloginemail;

    @FXML
    private PasswordField txtloginpassword;

    @FXML
    void createAccountOnAction(ActionEvent event) throws IOException {
        Stage s1 = new Stage();
        s1.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/CreateUserAccountInterface.fxml"))));
        s1.show();
    }

    @FXML
    void userforgotpwOnAction(ActionEvent event) {

    }

    @FXML
    void userloginOnAction(ActionEvent event) throws IOException, SQLException {
        logindashboard();
    }

    public void logindashboard() throws SQLException, IOException {
        String email = txtloginemail.getText();
        String password = txtloginpassword.getText();

        if (email.isEmpty() && password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setHeaderText("Missing Information");
            alert.setContentText("Please enter both email and password");
            alert.showAndWait();
        }

        if (authenticateUser(email, password)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login Successful");
            alert.setHeaderText("Welcome!");
            alert.setContentText("Login successful for: " + email);
            alert.showAndWait();
            clearField();

                Stage s1 = new Stage();
                s1.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ServiceListuser.fxml"))));
                s1.show();

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText("Authentication Error");
            alert.setContentText("Invalid email or password. Please try again.");
            alert.showAndWait();
        }
    }

    private boolean authenticateUser(String email, String password) throws SQLException {

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE email = ? AND password = ?");
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } finally {

        }
    }
    public void clearField(){
        txtloginemail.clear();
        txtloginpassword.clear();
    }
}