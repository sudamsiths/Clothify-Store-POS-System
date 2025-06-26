package controller;

import DTO.Supplier;
import Service.ServiceFactory;
import Service.custom.SupplierService;
import db.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import util.CRUDutil;
import util.ServiceType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddNewSupplierController {

    public TextField txtid;
    @FXML
    private TextField txtcompanyname;

    @FXML
    private TextField txtemail;

    @FXML
    private TextField txtitem;

    @FXML
    private TextField txtname;

    SupplierService supplierService = ServiceFactory.getInstance().getServiceType(ServiceType.Supplier);

    @FXML
    void ViewSuppliersOnAction(ActionEvent event) {

    }

    @FXML
    void btnAddOnAction(ActionEvent event) throws SQLException {
        addSuppliers();
    }
    @FXML
    public void initialize() throws SQLException {
            String nextId = generateNextSupplierID();
            txtid.setText(nextId);
            txtid.setEditable(false);

    }
    public String IdIncrement(String id) {
        if (id != null && id.length() >= 2 && id.startsWith("S")) {
            String numberPart = id.substring(1);
            try {
                int number = Integer.parseInt(numberPart);
                return String.format("S%03d", number + 1);
            } catch (NumberFormatException e) {
                return "S001";
            }
        }
        return "S001";
    }
    private String generateNextSupplierID() throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            connection = DBConnection.getInstance().getConnection();
            stmt = connection.prepareStatement(
                    "SELECT supplier_id FROM suppliers ORDER BY supplier_id DESC LIMIT 1"
            );

            rs = stmt.executeQuery();
            if (rs.next()) {
                return IdIncrement(rs.getString("supplier_id"));
            } else {
                return "S001";
            }
        } catch (SQLException e) {
            System.err.println("Error generating next supplier ID: " + e.getMessage());
            throw e;
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
        }
    }
    void addSuppliers() throws SQLException {

            String id=txtid.getText();
            String name=txtname.getText();
            String companyname=txtcompanyname.getText();
            String email=txtemail.getText();
            String item=txtitem.getText();

            Supplier supplier =new Supplier(id,name,companyname,email,item);
            Boolean b = supplierService.addSupplier(supplier);
            if (b) {
                showAlert1("Supplier Added", "Supplier added successfully with ID: " + txtid.getText());
                clearFields();
                String nextId = generateNextSupplierID();
                txtid.setText(nextId);
            } else {
                showAlert("Supplier Added ", "Supplier added Fail");

            }
    }
    private void clearFields() {
        txtname.clear();
        txtcompanyname.clear();
        txtemail.clear();
        txtitem.clear();
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void showAlert1(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }
}
