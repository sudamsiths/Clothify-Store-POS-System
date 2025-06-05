package controller;

import Service.ServiceFactory;
import Service.custom.EmployeeService;
import Service.custom.impl.EmployeeServiceimpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import DTO.Employee;
import util.CRUDutil;
import util.ServiceType;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UpdateCustomerDetailsController {

    public TextField txtsearch;
    @FXML
    private TableColumn coladdress;
    @FXML
    private TableColumn colcontact;
    @FXML
    private TableColumn coldob;
    @FXML
    private TableColumn colid;
    @FXML
    private TableColumn colname;
    @FXML
    private TableColumn colnic;
    @FXML
    private  TableView<Employee> tblview;

    EmployeeService employeeService = ServiceFactory.getInstance().getServiceType(ServiceType.Employee);

    @FXML
    public void loadTable() throws SQLException {
        EmployeeServiceimpl employeeimpl = new EmployeeServiceimpl();
        List<Employee> employeeList = employeeimpl.getAll();

        ObservableList<Employee> objectObservableList = FXCollections.observableArrayList(employeeList);

        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colname.setCellValueFactory(new PropertyValueFactory<>("name"));
        colnic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        coladdress.setCellValueFactory(new PropertyValueFactory<>("address"));
        coldob.setCellValueFactory(new PropertyValueFactory<>("DOB"));
        colcontact.setCellValueFactory(new PropertyValueFactory<>("contactno"));

        tblview.setItems(objectObservableList);
    }


    @FXML
    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException {
        Employee selectedCustomer = (Employee) tblview.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            showAlert("Please select an employee to delete");
            return;
        }

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Delete Confirmation");
        confirmationAlert.setHeaderText("Delete Employee");
        confirmationAlert.setContentText("Are you sure you want to delete this employee?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            CRUDutil.execute("delete from customer where id = ?", selectedCustomer.getId());
            loadTable();
        }

    }

    public void btnsearchOnAction(ActionEvent actionEvent) throws SQLException {
        String search = txtsearch.getText();


        Employee foundEmployee = employeeService.search(search);

        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colname.setCellValueFactory(new PropertyValueFactory<>("name"));
        colnic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        coladdress.setCellValueFactory(new PropertyValueFactory<>("address"));
        coldob.setCellValueFactory(new PropertyValueFactory<>("DOB"));
        colcontact.setCellValueFactory(new PropertyValueFactory<>("contactno"));

        ObservableList<Employee> employeeList = FXCollections.observableArrayList();
        if (foundEmployee != null) {
            employeeList.add(foundEmployee);
        }
        tblview.setItems(employeeList);
    }


    public void btnviewallOnAction(ActionEvent actionEvent) throws SQLException {

        loadTable();
    }
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}