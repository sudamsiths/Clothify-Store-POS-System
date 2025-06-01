package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.entity.Employee;
import util.CRUDutil;

import java.sql.ResultSet;
import java.sql.SQLException;

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
        ObservableList<Employee> objectObservableList = FXCollections.observableArrayList();

        ResultSet resultSet = CRUDutil.execute("select * from customer");
        while (resultSet.next()) {
            objectObservableList.add(new Employee(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)
            ));
        }
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colname.setCellValueFactory(new PropertyValueFactory<>("name"));
        colnic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        coladdress.setCellValueFactory(new PropertyValueFactory<>("address"));
        coldob.setCellValueFactory(new PropertyValueFactory<>("dob"));
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
        ObservableList<Employee> objectObservableList = FXCollections.observableArrayList();

        ResultSet resultSet = CRUDutil.execute("select * from customer where id = ?", search);
        while (resultSet.next()){
            objectObservableList.add(new Employee(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6)
            ));
        }
        tblview.setItems(objectObservableList);
    }

    public void btnviewallOnAction(ActionEvent actionEvent) throws SQLException {
        loadTable();
    }
}