package controller;

import DTO.Products;
import Service.ServiceFactory;
import Service.custom.ProductService;
import Service.custom.impl.ProductServiceimpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import util.CRUDutil;
import util.ServiceType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class RemoveUpdateProductController {

    @FXML
    private TableColumn colcategory;

    @FXML
    private TableColumn colid;

    @FXML
    private TableColumn colprice;

    @FXML
    private TableColumn colqty;

    @FXML
    private TableColumn colsize;

    @FXML
    private TableColumn colsupplierid;

    @FXML
    private TableColumn coltitle;

    @FXML
    private TableView tblview;

    @FXML
    private TextField txtcategory;

    @FXML
    private TextField txtprice;

    @FXML
    private TextField txtproducttitle;

    @FXML
    private TextField txtqty;

    @FXML
    private TextField txtsearch;

    @FXML
    private TextField txtsize;

    @FXML
    private TextField txtsupplierid;

    ProductService productService = ServiceFactory.getInstance().getServiceType(ServiceType.Product);

    @FXML
    public void loadTable() throws SQLException {
        ProductServiceimpl productServiceimpl = new ProductServiceimpl();
        List<Products> products = productServiceimpl.getAllProduct();

        ObservableList<Products> objectObservableList = FXCollections.observableArrayList(products);

        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colsupplierid.setCellValueFactory(new PropertyValueFactory<>("supplier_id"));
        coltitle.setCellValueFactory(new PropertyValueFactory<>("name"));
        colcategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colqty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colsize.setCellValueFactory(new PropertyValueFactory<>("size"));
        colprice.setCellValueFactory(new PropertyValueFactory<>("price"));

        tblview.setItems(objectObservableList);
    }

    @FXML
    void btndeleteonAction(ActionEvent event) throws SQLException {
        Products products = (Products) tblview.getSelectionModel().getSelectedItem();
        if (products == null) {
            showAlert("Please select an Product to delete");
            return;
        }

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Delete Confirmation");
        confirmationAlert.setHeaderText("Delete Product");
        confirmationAlert.setContentText("Are you sure you want to delete this Product?");

        Optional<ButtonType> result = confirmationAlert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            CRUDutil.execute("delete from products where id = ?", products.getId());
            loadTable();
        }

    }

    @FXML
    void btnsearchOnAction() throws SQLException {
        String search = txtsearch.getText();
        ResultSet resultSet = CRUDutil.execute("SELECT * FROM products WHERE id = ?", search);

        if (resultSet.next()) {
            txtsupplierid.setText(resultSet.getString("supplier_id"));
            txtproducttitle.setText(resultSet.getString("name"));
            txtcategory.setText(resultSet.getString("category"));
            txtqty.setText(resultSet.getString("qty"));
            txtprice.setText(resultSet.getString("price"));
            txtsize.setText(resultSet.getString("size"));

            showAlert("Product Found successfully");

        } else {
            showAlert("Product Not Found");
        }
    }

    @FXML
    void updatebtnOnAction(ActionEvent event) throws SQLException {
        String productId = txtsearch.getText();
        String supplierid = txtsupplierid.getText();
        String title = txtproducttitle.getText();
        String category = txtcategory.getText();
        String size = txtsize.getText();
        String qtyText = txtqty.getText();
        String priceText = txtprice.getText();

        if (productId == null || productId.trim().isEmpty()) {
            showAlert("Product ID is required for update");
            return;
        }

        boolean b = CRUDutil.execute("UPDATE products SET supplier_id=?, name=?, category=?, size=?, price=?, qty=? WHERE id=?",
                supplierid, title, category, size, priceText, qtyText, productId);

        if (b) {
            showAlert("Product updated successfully");
        } else {
            showAlert("Update failed");
        }
        clearFields();
    }

    public void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        txtsupplierid.clear();
        txtproducttitle.clear();
        txtcategory.clear();
        txtqty.clear();
        txtprice.clear();
        txtsearch.clear();
        txtsize.clear();
    }

    public void btnviewallOnAction(ActionEvent actionEvent) throws SQLException {
        loadTable();
    }
}
