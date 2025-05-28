package controller;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;
import util.CRUDutil;

import java.sql.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddUserInterfaceController {

    @FXML
    private DatePicker txtDOB;

    @FXML
    private TextField txtNIC;

    @FXML
    private TextField txtaddress;

    @FXML
    private TextField txtconatcNO;

    @FXML
    private TextField txtemail;

    @FXML
    private TextField txtid;

    @FXML
    private TextField txtname;

    @FXML
    private PasswordField txtpassword;

    @FXML
    public void initialize() throws SQLException {

        String nextId = generateNextCustomerID();
        txtid.setText(nextId);
        txtid.setEditable(false);
    }

    public String IdIncrement(String id) {
        try {
            if (id != null && id.length() >= 2 && id.startsWith("C")) {
                String numberPart = id.substring(1);
                int number = Integer.parseInt(numberPart);
                return String.format("C%03d", number + 1);
            }
        } catch (NumberFormatException e) {
        }
        return "C001";
    }

    private String generateNextCustomerID() throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            connection = DBConnection.getInstance().getConnection();
            stmt = connection.prepareStatement(
                    "SELECT id FROM customer ORDER BY id DESC LIMIT 1"
            );

            rs = stmt.executeQuery();
            if (rs.next()) {
                return IdIncrement(rs.getString("id"));
            } else {
                return "C001";
            }
        } finally {
        }
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        try {
            String nextId = txtid.getText();

            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO customer VALUES (?,?,?,?,?,?,?,?)");

            preparedStatement.setString(1, nextId);
            preparedStatement.setString(2, txtname.getText());
            preparedStatement.setString(3, txtNIC.getText());
            preparedStatement.setString(4, txtaddress.getText());
            if (txtDOB.getValue() != null) {
                preparedStatement.setDate(5, Date.valueOf(txtDOB.getValue()));
            } else {
                preparedStatement.setNull(5, java.sql.Types.DATE);
            }
            preparedStatement.setString(6, txtconatcNO.getText());
            preparedStatement.setString(7, txtemail.getText());
            preparedStatement.setString(8, txtpassword.getText());

            int i = preparedStatement.executeUpdate();
            if (i > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Customer Added Successfully");
                alert.setContentText("Customer ID: " + nextId);
                alert.showAndWait();
            }
            cleartext();

        } catch (SQLException e) {
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        cleartext();

    }

    @FXML
    void btnGotoStoreOnAction(ActionEvent event) throws IOException {
        Stage s1 = new Stage();
        s1.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/AddProductInterface.fxml"))));
        s1.show();

    }

    public void cleartext() {
        txtname.clear();
        txtNIC.clear();
        txtaddress.clear();
        txtemail.clear();
        txtpassword.clear();
        txtconatcNO.clear();
        txtDOB.setValue(null);
        txtDOB.getEditor().clear();
    }

    public void ViewCustomersOnAction(ActionEvent actionEvent) throws IOException {
        Stage s1=new Stage();
        s1.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/UpdateCustomerdetails.fxml"))));
        s1.show();
    }
}
