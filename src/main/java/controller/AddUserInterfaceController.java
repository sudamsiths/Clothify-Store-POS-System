package controller;

import Service.ServiceFactory;
import Service.custom.EmployeeService;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import DTO.Employee;
import util.ServiceType;

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


    EmployeeService customerService = ServiceFactory.getInstance().getServiceType(ServiceType.Employee);

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
        try {
            String nextId = txtid.getText();
            String name = txtname.getText();
            String nic = txtNIC.getText();
            String address = txtaddress.getText();
            String contactNo = txtconatcNO.getText();

            Date dob = null;
            if (txtDOB.getValue() != null) {
                dob = Date.valueOf(txtDOB.getValue());
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Validation Error");
                alert.setHeaderText("Missing Date");
                alert.setContentText("Please select a date of birth.");
                alert.showAndWait();
                return;
            }

            Employee employee = new Employee(nextId, name, nic, address, dob, contactNo);
            Boolean b = customerService.addEmployee(employee);

            if (b) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Employee Added Successfully");
                alert.setContentText("Employee ID: " + nextId);
                alert.showAndWait();

                cleartext();
                initialize();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Failed to Add Employee");
                alert.setContentText("Could not add employee. Please try again.");
                alert.showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace(); // For debugging
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Database Error");
            alert.setHeaderText("SQL Exception Occurred");
            alert.setContentText("Error: " + e.getMessage());
            alert.showAndWait();
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
