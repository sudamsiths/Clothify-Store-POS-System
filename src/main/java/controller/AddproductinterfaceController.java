package controller;

import DTO.Employee;
import DTO.Products;
import Service.ServiceFactory;
import Service.custom.EmployeeService;
import Service.custom.ProductService;
import db.DBConnection;
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

public class AddproductinterfaceController {

    public TextField txtidAdditem;
    public TextField txtitemname;
    public TextField txtsizeitems;
    public TextField txtpriceadditems;
    public TextField txtsupplyerid;
    public TextField txtqtyfield;
    @FXML
    private ImageView productImage;
    @FXML
    private ChoiceBox<String> categoryChoiceBox;


    ProductService productService = ServiceFactory.getInstance().getServiceType(ServiceType.Product);

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

    public void initialize() throws SQLException {
        String nextId = generateNextProductID();
        txtidAdditem.setText(nextId);
        txtidAdditem.setEditable(false);
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
        String nextId= txtidAdditem.getText();
        String supplierid=txtsupplyerid.getText();
        String name=txtitemname.getText();
        String category=categoryChoiceBox.getValue();
        String size=txtsizeitems.getText();
        Double price=Double.parseDouble(txtpriceadditems.getText());
        Integer qty=Integer.parseInt(txtqtyfield.getText());
        String image = selectedImagePath != null ? selectedImagePath : "";

        Products products = new Products(nextId, supplierid,name,category,size,price,qty,image);
        boolean b = productService.addProduct(products);

        if (b) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Product Added Successfully");
            alert.setContentText("Product ID: " + nextId);
            alert.showAndWait();

            initialize();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to Add Product");
            alert.setContentText("Could not add Product. Please try again.");
            alert.showAndWait();
        }
    }

    public void btnShowItemsOnAction(ActionEvent actionEvent) throws IOException {
        Stage s1=new Stage();
        s1.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ShowProductInterface.fxml"))));
        s1.show();
    }
}
