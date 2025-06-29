package controller.improved;

import DTO.Employee;
import entity.UserEntity;
import service.CustomerService;
import service.UserService;
import util.AlertUtil;
import util.IdGenerator;
import util.ValidationUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.sql.Date;
import java.time.LocalDate;

public class ImprovedAddUserController {

    @FXML private TextField txtid;
    @FXML private TextField txtname;
    @FXML private TextField txtNIC;
    @FXML private TextField txtaddress;
    @FXML private DatePicker txtDOB;
    @FXML private TextField txtconatcNO;

    private final CustomerService customerService;
    private final UserService userService;

    public ImprovedAddUserController() {
        this.customerService = new CustomerService();
        this.userService = new UserService();
    }

    @FXML
    public void initialize() {
        generateNextCustomerId();
        txtid.setEditable(false);
    }

    private void generateNextCustomerId() {
        try {
            long customerCount = customerService.getCustomerCount();
            String nextId = String.format("C%03d", customerCount + 1);
            txtid.setText(nextId);
        } catch (Exception e) {
            txtid.setText(IdGenerator.generateCustomerId());
        }
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        if (validateInput()) {
            addCustomer();
        }
    }

    private boolean validateInput() {
        String name = txtname.getText();
        String nic = txtNIC.getText();
        String address = txtaddress.getText();
        String contactNo = txtconatcNO.getText();
        LocalDate dob = txtDOB.getValue();

        if (!ValidationUtil.isNotEmpty(name)) {
            AlertUtil.showErrorAlert("Validation Error", "Name is required");
            return false;
        }

        if (!ValidationUtil.isValidNIC(nic)) {
            AlertUtil.showErrorAlert("Validation Error", "Invalid NIC format");
            return false;
        }

        if (!ValidationUtil.isNotEmpty(address)) {
            AlertUtil.showErrorAlert("Validation Error", "Address is required");
            return false;
        }

        if (!ValidationUtil.isValidPhone(contactNo)) {
            AlertUtil.showErrorAlert("Validation Error", "Invalid phone number format");
            return false;
        }

        if (dob == null) {
            AlertUtil.showErrorAlert("Validation Error", "Date of birth is required");
            return false;
        }

        // Check if customer with same NIC already exists
        Employee existingCustomer = customerService.findCustomerByNic(nic);
        if (existingCustomer != null) {
            AlertUtil.showErrorAlert("Validation Error", "Customer with this NIC already exists");
            return false;
        }

        return true;
    }

    private void addCustomer() {
        try {
            String id = txtid.getText();
            String name = txtname.getText();
            String nic = txtNIC.getText();
            String address = txtaddress.getText();
            String contactNo = txtconatcNO.getText();
            Date dob = Date.valueOf(txtDOB.getValue());

            Employee employee = new Employee(id, name, nic, address, dob, contactNo);
            
            if (customerService.addCustomer(employee)) {
                AlertUtil.showSuccessAlert("Success", "Customer added successfully with ID: " + id);
                clearFields();
                generateNextCustomerId();
            } else {
                AlertUtil.showErrorAlert("Error", "Failed to add customer. Please try again.");
            }
        } catch (Exception e) {
            AlertUtil.showErrorAlert("Error", "An unexpected error occurred: " + e.getMessage());
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtname.clear();
        txtNIC.clear();
        txtaddress.clear();
        txtconatcNO.clear();
        txtDOB.setValue(null);
    }
}