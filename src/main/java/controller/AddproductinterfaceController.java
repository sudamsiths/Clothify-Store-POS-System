package controller;

import DTO.Products;
import DTO.Supplier;
import Service.ServiceFactory;
import Service.custom.ProductService;
import Service.custom.SupplierService;
import com.jfoenix.controls.JFXComboBox;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import util.ServiceType;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AddproductinterfaceController{

    public TextField txtidAdditem;
    public TextField txtitemname;
    public TextField txtsizeitems;
    public TextField txtpriceadditems;
    public TextField txtsupplyerid;
    public TextField txtqtyfield;
    public JFXComboBox cmbSupplierid;
    @FXML
    private ImageView productImage;
    @FXML
    private ChoiceBox<String> categoryChoiceBox;


    ProductService productService = ServiceFactory.getInstance().getServiceType(ServiceType.Product);
    SupplierService supplierService =ServiceFactory.getInstance().getServiceType(ServiceType.Supplier);

    private String selectedImagePath;

    @FXML
    private void handleChooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Product Image");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            selectedImagePath = selectedFile.getAbsolutePath();

            Image image = new Image(selectedFile.toURI().toString());
            productImage.setImage(image);
        }
    }

    public void initialize() {
        try {
            String nextId = generateNextProductID();
            txtidAdditem.setText(nextId);
            txtidAdditem.setEditable(false);
            loadCustomerIDs();

            cmbSupplierid.getSelectionModel().selectedItemProperty().addListener((observableValue, oldVal, newVal) -> {
                if (newVal != null) {
                    setValuesToSupplierText((String) newVal);
                }
            });
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Initialization Error");
            alert.setHeaderText("Failed to initialize form");
            alert.setContentText("Error: " + e.getMessage());
            alert.showAndWait();
        }
    }

    public String IdIncrement(String id) {
        if (id != null && id.length() >= 2 && id.startsWith("P")) {
            String numberPart = id.substring(1);
            int number = Integer.parseInt(numberPart);
            return String.format("P%03d", number + 1);
        }
        return "P001";
    }

    private String generateNextProductID() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement stmt = connection.prepareStatement(
                "SELECT id FROM products ORDER BY id DESC LIMIT 1"
        );

        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return IdIncrement(rs.getString("id"));
        } else {
            return "P001";
        }
    }

    public void btnItemAddOnAction(ActionEvent actionEvent) throws SQLException {

            String nextId = txtidAdditem.getText();

            String supplierid = (String) cmbSupplierid.getSelectionModel().getSelectedItem();

            String name = txtitemname.getText();
            String category = categoryChoiceBox.getValue();
            String size = txtsizeitems.getText();
            Double price = Double.parseDouble(txtpriceadditems.getText());
            Integer qty = Integer.parseInt(txtqtyfield.getText());
            String image = selectedImagePath != null ? selectedImagePath : "";

            if (supplierid == null || supplierid.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText("Please select a supplier");
                alert.setContentText("You must select a supplier from the dropdown.");
                alert.showAndWait();
                return;
            }

            Products products = new Products(nextId, supplierid, name, category, size, price, qty, image);
            boolean b = productService.addProduct(products);

            if (b) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Product Added Successfully");
                alert.setContentText("Product ID: " + nextId + "\nSupplier ID: " + supplierid);
                alert.showAndWait();

                clearForm();
                initialize();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Failed to Add Product");
                alert.setContentText("Could not add Product. Please try again.");
                alert.showAndWait();
            }
    }

        private void clearForm() {
        txtitemname.clear();
        txtsizeitems.clear();
        txtpriceadditems.clear();
        txtqtyfield.clear();
        categoryChoiceBox.getSelectionModel().clearSelection();
        cmbSupplierid.getSelectionModel().clearSelection();
        productImage.setImage(null);
        selectedImagePath = null;
    }

    public void btnShowItemsOnAction(ActionEvent actionEvent) throws IOException {
        Stage s1=new Stage();
        s1.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ShowProductInterface.fxml"))));
        s1.show();
    }

    private void loadCustomerIDs() throws SQLException {
        List<String> customerIds = supplierService.getCustomerIds();
        cmbSupplierid.setItems(FXCollections.observableArrayList(customerIds));
    }
    private void setValuesToSupplierText(String supplierId) {
        try {
            Supplier supplier = supplierService.searchById(supplierId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
