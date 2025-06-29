package controller.improved;

import DTO.Products;
import service.ProductService;
import service.SupplierService;
import util.AlertUtil;
import util.IdGenerator;
import util.ValidationUtil;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import com.jfoenix.controls.JFXComboBox;

import java.io.File;
import java.util.List;

public class ImprovedProductController {

    @FXML private TextField txtidAdditem;
    @FXML private TextField txtitemname;
    @FXML private TextField txtsizeitems;
    @FXML private TextField txtpriceadditems;
    @FXML private TextField txtqtyfield;
    @FXML private JFXComboBox<String> cmbSupplierid;
    @FXML private ImageView productImage;
    @FXML private ChoiceBox<String> categoryChoiceBox;

    private final ProductService productService;
    private final SupplierService supplierService;
    private String selectedImagePath;

    public ImprovedProductController() {
        this.productService = new ProductService();
        this.supplierService = new SupplierService();
    }

    @FXML
    public void initialize() {
        generateNextProductId();
        loadSupplierIds();
        txtidAdditem.setEditable(false);
    }

    private void generateNextProductId() {
        try {
            long productCount = productService.getProductCount();
            String nextId = String.format("P%03d", productCount + 1);
            txtidAdditem.setText(nextId);
        } catch (Exception e) {
            txtidAdditem.setText(IdGenerator.generateProductId());
        }
    }

    private void loadSupplierIds() {
        try {
            List<String> supplierIds = supplierService.getAllSupplierIds();
            cmbSupplierid.setItems(FXCollections.observableArrayList(supplierIds));
        } catch (Exception e) {
            AlertUtil.showErrorAlert("Error", "Failed to load supplier IDs: " + e.getMessage());
        }
    }

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
            // Load and display image preview
        }
    }

    @FXML
    public void btnItemAddOnAction(ActionEvent actionEvent) {
        if (validateInput()) {
            addProduct();
        }
    }

    private boolean validateInput() {
        String name = txtitemname.getText();
        String category = categoryChoiceBox.getValue();
        String size = txtsizeitems.getText();
        String priceText = txtpriceadditems.getText();
        String qtyText = txtqtyfield.getText();
        String supplierId = cmbSupplierid.getSelectionModel().getSelectedItem();

        if (!ValidationUtil.isNotEmpty(name)) {
            AlertUtil.showErrorAlert("Validation Error", "Product name is required");
            return false;
        }

        if (!ValidationUtil.isNotEmpty(category)) {
            AlertUtil.showErrorAlert("Validation Error", "Category is required");
            return false;
        }

        if (!ValidationUtil.isNotEmpty(size)) {
            AlertUtil.showErrorAlert("Validation Error", "Size is required");
            return false;
        }

        if (!ValidationUtil.isNotEmpty(supplierId)) {
            AlertUtil.showErrorAlert("Validation Error", "Supplier must be selected");
            return false;
        }

        try {
            Double price = Double.parseDouble(priceText);
            if (!ValidationUtil.isPositiveNumber(price)) {
                AlertUtil.showErrorAlert("Validation Error", "Price must be a positive number");
                return false;
            }
        } catch (NumberFormatException e) {
            AlertUtil.showErrorAlert("Validation Error", "Invalid price format");
            return false;
        }

        try {
            Integer qty = Integer.parseInt(qtyText);
            if (!ValidationUtil.isPositiveInteger(qty)) {
                AlertUtil.showErrorAlert("Validation Error", "Quantity must be a positive number");
                return false;
            }
        } catch (NumberFormatException e) {
            AlertUtil.showErrorAlert("Validation Error", "Invalid quantity format");
            return false;
        }

        return true;
    }

    private void addProduct() {
        try {
            String id = txtidAdditem.getText();
            String supplierId = cmbSupplierid.getSelectionModel().getSelectedItem();
            String name = txtitemname.getText();
            String category = categoryChoiceBox.getValue();
            String size = txtsizeitems.getText();
            Double price = Double.parseDouble(txtpriceadditems.getText());
            Integer qty = Integer.parseInt(txtqtyfield.getText());
            String image = selectedImagePath != null ? selectedImagePath : "";

            Products product = new Products(id, supplierId, name, category, size, price, image, qty);

            if (productService.addProduct(product)) {
                AlertUtil.showSuccessAlert("Success", "Product added successfully with ID: " + id);
                clearForm();
                generateNextProductId();
            } else {
                AlertUtil.showErrorAlert("Error", "Failed to add product. Please try again.");
            }
        } catch (Exception e) {
            AlertUtil.showErrorAlert("Error", "An unexpected error occurred: " + e.getMessage());
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
}