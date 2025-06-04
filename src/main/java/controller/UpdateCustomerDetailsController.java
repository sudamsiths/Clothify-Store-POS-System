package controller;

import Servicenew.ServiceFactory;
import Servicenew.custom.EmployeeService;
import Servicenew.custom.impl.Employeeimpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Employee;
import util.CRUDutil;
import util.ServiceType;

import java.sql.SQLException;
import java.util.List;

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
    private TableView tblview;


    @FXML
    public void loadTable() throws SQLException {
        Employeeimpl employeeimpl = new Employeeimpl();
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

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException {
        Employee selectedCustomer = (Employee) tblview.getSelectionModel().getSelectedItem();
        CRUDutil.execute("delete from customer where id = ?", selectedCustomer.getId());
        loadTable();
    }

    public void btnsearchOnAction(ActionEvent actionEvent) throws SQLException {
        String search = txtsearch.getText();

        EmployeeService employeeService = ServiceFactory.getInstance().getServiceType(ServiceType.Employee);
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
}