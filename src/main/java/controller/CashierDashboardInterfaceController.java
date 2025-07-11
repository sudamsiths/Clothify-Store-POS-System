package controller;

import DTO.Products;
import Service.ServiceFactory;
import Service.custom.ProductService;
import Service.custom.impl.ProductServiceimpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import util.ServiceType;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class CashierDashboardInterfaceController {

    public TextField txtsearch;
    @FXML
    private Button btnClearSearch;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnRefreshProducts;

    @FXML
    private Button btnSearch;

    @FXML
    private Button btnSearchProducts;

    @FXML
    private Button btnViewCategories;

    @FXML
    private Button btnViewProductDetails;

    @FXML
    private Button btnViewProducts;

    @FXML
    private TableColumn colcategory;

    @FXML
    private TableColumn colid;

    @FXML
    private TableColumn colprice;

    @FXML
    private TableColumn colqty;

    @FXML
    private TableColumn coltitle;

    @FXML
    private Label lblCashierName;

    @FXML
    private TableColumn solsize;

    @FXML
    private TableView tblProducts;

    @FXML
    private TextField txtSearchProduct;

    RemoveUpdateProductController removeUpdateProductController = new RemoveUpdateProductController();
    ProductService productService = ServiceFactory.getInstance().getServiceType(ServiceType.Product);

    @FXML
    void handleClearSearch(ActionEvent event) {
        txtsearch.clear();
    }

    @FXML
    void handleLogout(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

        Stage loginStage = new Stage();
        loginStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/CashierLoginInterface.fxml"))));
        loginStage.show();
    }

    @FXML
    void handleRefreshProducts(ActionEvent event) throws SQLException {
        loadTable();
    }

    @FXML
    void handleSearchProduct(ActionEvent event) throws SQLException {
        String search = txtsearch.getText();

        Products foundProduct = productService.searchProduct(search);

        ObservableList<Products> products = FXCollections.observableArrayList();

        if (foundProduct != null) {
            products.add(foundProduct);
            removeUpdateProductController.showAlert("Product Found Successfully");
            colid.setCellValueFactory(new PropertyValueFactory<>("id"));
            coltitle.setCellValueFactory(new PropertyValueFactory<>("name"));
            colcategory.setCellValueFactory(new PropertyValueFactory<>("category"));
            solsize.setCellValueFactory(new PropertyValueFactory<>("size"));
            colprice.setCellValueFactory(new PropertyValueFactory<>("price"));
            colqty.setCellValueFactory(new PropertyValueFactory<>("qty"));

            tblProducts.setItems(products);
        } else {
            removeUpdateProductController.showAlert(search + " Product Not Found ");
        }

    }


    @FXML
    void handleSearchProducts(ActionEvent event) {

    }

    @FXML
    void handleViewCategories(ActionEvent event) {

    }

    @FXML
    void handleViewProductDetails(ActionEvent event) {

    }

    @FXML
    public void loadTable() throws SQLException {
        ProductServiceimpl productServiceimpl = new ProductServiceimpl();
        List<Products> products = productServiceimpl.getAllProduct();

        ObservableList<Products> objectObservableList = FXCollections.observableArrayList(products);

        colid.setCellValueFactory(new PropertyValueFactory<>("id"));
        coltitle.setCellValueFactory(new PropertyValueFactory<>("name"));
        colcategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        solsize.setCellValueFactory(new PropertyValueFactory<>("size"));
        colprice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colqty.setCellValueFactory(new PropertyValueFactory<>("qty"));

        System.out.println(objectObservableList);

        tblProducts.setItems(objectObservableList);
    }

    @FXML
    void handleViewProducts(ActionEvent event) throws SQLException {
        loadTable();
    }

    public void btnplaceorderOnAction(ActionEvent actionEvent) throws IOException {
        Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/PlaceOrderFromInterface.fxml"))));
        stage.show();
        currentStage.close();
    }
}
