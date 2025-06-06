package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class CashierDashboardInterfaceController {

    @FXML
    private Button btnAddProducts;

    @FXML
    private Button btnClearSearch;

    @FXML
    private Button btnLogout;

    @FXML
    private Button btnManageEmployees;

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
    private TableColumn<?, ?> colCategory;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private TableColumn<?, ?> colProductId;

    @FXML
    private TableColumn<?, ?> colProductName;

    @FXML
    private TableColumn<?, ?> colQuantity;

    @FXML
    private TableColumn<?, ?> colSize;

    @FXML
    private Label lblCashierName;

    @FXML
    private Label lblLastUpdated;

    @FXML
    private Label lblProductCount;

    @FXML
    private TableView<?> tblProducts;

    @FXML
    private TextField txtSearchProduct;

    @FXML
    void handleClearSearch(ActionEvent event) {

    }

    @FXML
    void handleLogout(ActionEvent event) {

    }

    @FXML
    void handleRefreshProducts(ActionEvent event) {

    }

    @FXML
    void handleSearchProduct(ActionEvent event) {

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
    void handleViewProducts(ActionEvent event) {

    }

}
