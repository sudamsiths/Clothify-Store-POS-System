package controller;

import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.*;

import java.io.IOException;

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
    private TextField txtid;

    @FXML
    private TextField txtname;

    @FXML
    public void initialize() throws SQLException {
        String nextId = generateNextCustomerID();
        txtid.setText(nextId);
        txtid.setEditable(false);
    }

    public String IdIncrement(String id) {
        if (id != null && id.length() >= 2 && id.startsWith("C")) {
            String numberPart = id.substring(1);
            int number = Integer.parseInt(numberPart);
            return String.format("C%03d", number + 1);
        }
        return "C001";
    }

    private String generateNextCustomerID() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement stmt = connection.prepareStatement(
                "SELECT id FROM customer ORDER BY id DESC LIMIT 1"
        );

        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return IdIncrement(rs.getString("id"));
        } else {
            return "C001";
        }
    }

    @FXML
    void btnAddOnAction(ActionEvent event) throws SQLException {
        String nextId = txtid.getText();

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(
                "INSERT INTO customer VALUES (?,?,?,?,?,?)");

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

        int i = preparedStatement.executeUpdate();
        if (i > 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Customer Added Successfully");
            alert.setContentText("Customer ID: " + nextId);
            alert.showAndWait();
        }
        cleartext();
        initialize();
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
