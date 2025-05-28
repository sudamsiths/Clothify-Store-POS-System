package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Customer;
import util.CRUDutil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
    private TableColumn colemail;

    @FXML
    private TableColumn colid;

    @FXML
    private TableColumn colname;

    @FXML
    private TableColumn colnic;

    @FXML
    private TableColumn colpassword;

    @FXML
    private TableView tblview;

    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        ResultSet resultSet = CRUDutil.execute("UPDATE customer SET name=?, nic=?, address=?, DOB=?, contactno=?, email=?, password=? WHERE id=?",colname,colnic,coladdress,coldob,colcontact,colemail,colpassword);

        loadTable();

    }
    public void loadTable() throws SQLException {
        ObservableList<Customer> objectObservableList = FXCollections.observableArrayList();


        ResultSet resultSet = CRUDutil.execute("select * from customer");
        while (resultSet.next()) {
            objectObservableList.add(new Customer(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getString(3),
                    resultSet.getString(4),
                    resultSet.getString(5),
                    resultSet.getString(6),
                    resultSet.getString(7),
                    resultSet.getString(8)
            ));
        }
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colname.setCellValueFactory(new PropertyValueFactory<>("name"));
        colnic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        coladdress.setCellValueFactory(new PropertyValueFactory<>("address"));
        coldob.setCellValueFactory(new PropertyValueFactory<>("DOB"));
        colcontact.setCellValueFactory(new PropertyValueFactory<>("contactno"));
        colemail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colpassword.setCellValueFactory(new PropertyValueFactory<>("password"));


        tblview.setItems(objectObservableList);
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) throws SQLException {
        deleteColumn();
    }
    public void deleteColumn() throws SQLException {
        Customer selectedCustomer = (Customer) tblview.getSelectionModel().getSelectedItem();
            CRUDutil.execute("delete from customer where id = ?", selectedCustomer.getId());
            tblview.getItems().remove(selectedCustomer);
    }

    public void btnsearchOnAction(ActionEvent actionEvent) throws SQLException {
        String search = txtsearch.getText();

        ResultSet resultSet = CRUDutil.execute("select * from customer where id = ?", search);
        ObservableList<Customer> objectObservableList = FXCollections.observableArrayList();

        while (resultSet.next()){
            objectObservableList.add(new Customer(
                    resultSet.getString("id"),
                    resultSet.getString("name"),
                    resultSet.getString("nic"),
                    resultSet.getString("address"),
                    resultSet.getString("DOB"),
                    resultSet.getString("contactno"),
                    resultSet.getString("email"),
                    resultSet.getString("password")
            ));
        }
        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colname.setCellValueFactory(new PropertyValueFactory<>("name"));
        colnic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        coladdress.setCellValueFactory(new PropertyValueFactory<>("address"));
        coldob.setCellValueFactory(new PropertyValueFactory<>("DOB"));
        colcontact.setCellValueFactory(new PropertyValueFactory<>("contactno"));
        colemail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colpassword.setCellValueFactory(new PropertyValueFactory<>("password"));

        tblview.setItems(objectObservableList);
    }
}
