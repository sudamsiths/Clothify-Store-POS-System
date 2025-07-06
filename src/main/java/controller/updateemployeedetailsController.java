package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import util.CRUDutil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class updateemployeedetailsController {

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
    private TextField txtsearchID;


    @FXML
    void btnSarchOnAction(ActionEvent event) throws SQLException {
        String searchId = txtsearchID.getText();

        if (searchId.isEmpty()) {
            showAlert("Please enter an ID to search");
            return;
        }

        ResultSet resultSet = CRUDutil.execute("SELECT * FROM customer WHERE id = ?", searchId);

        if (resultSet.next()) {
            txtid.setText(resultSet.getString("id"));
            txtname.setText(resultSet.getString("name"));
            txtNIC.setText(resultSet.getString("nic"));
            txtaddress.setText(resultSet.getString("address"));
            txtconatcNO.setText(resultSet.getString("contactno"));

            String dobString = resultSet.getString("DOB");
            if (dobString != null) {
                txtDOB.setValue(LocalDate.parse(dobString));
            }

            showAlert("Employee found successfully");
        } else {
            clearFields();
            showAlert("Employee not found");
        }
    }

    @FXML
    void updatebtnOnAction(ActionEvent event) throws SQLException {
        String id = txtid.getText();
        String name = txtname.getText();
        String nic = txtNIC.getText();
        String address = txtaddress.getText();
        String contactNo = txtconatcNO.getText();
        LocalDate dob = txtDOB.getValue();

        if (name.isEmpty() || nic.isEmpty() || address.isEmpty() ||
                contactNo.isEmpty() || dob == null) {
            showAlert("Please fill all fields");
            return;
        }

        String sql = "UPDATE customer SET name=?, nic=?, address=?, dob=?, contactno=? WHERE id=?";

        Boolean result = CRUDutil.execute(sql, name, nic, address, dob.toString(), contactNo, id);

        if (result) {
            showAlert("Customer updated successfully");
        } else {
            showAlert("Update failed");
        }
    }

    private void clearFields() {
        txtid.clear();
        txtname.clear();
        txtNIC.clear();
        txtaddress.clear();
        txtconatcNO.clear();
        txtDOB.setValue(null);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}