package controller;

import DTO.*;
import Service.ServiceFactory;
import Service.custom.EmployeeService;
import Service.custom.OrderService;
import Service.custom.ProductService;
import com.jfoenix.controls.JFXComboBox;
import db.DBConnection;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import util.ServiceType;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class PlaceOrderFormController implements Initializable {

    public TextField txtOrderID;
    @FXML
    private JFXComboBox cmdcustomerid;

    @FXML
    private JFXComboBox cmditemcode;

    @FXML
    private TableColumn colcategory;

    @FXML
    private TableColumn coldescription;

    @FXML
    private TableColumn colitemcode;

    @FXML
    private TableColumn colqtyonhand;

    @FXML
    private TableColumn colsize;

    @FXML
    private TableColumn coltotal;

    @FXML
    private TableColumn colunitprice;

    @FXML
    private TextField customername;

    @FXML
    private Label lbldate;

    @FXML
    private Label lbldate1;

    @FXML
    private Label lbldate11;

    @FXML
    private Label lbltime;

    @FXML
    private Label lbltotal;

    @FXML
    private TableView tblitems;

    @FXML
    private TextField txtSize;

    @FXML
    private TextField txtaddress;

    @FXML
    private TextField txtcategory;

    @FXML
    private TextField txtdescription;

    @FXML
    private TextField txtqty;

    @FXML
    private TextField txtstock;

    @FXML
    private TextField txtunit;

    ProductService productService = ServiceFactory.getInstance().getServiceType(ServiceType.Product);
    EmployeeService employeeService = ServiceFactory.getInstance().getServiceType(ServiceType.Employee);
    OrderService orderService = ServiceFactory.getInstance().getServiceType(ServiceType.Order);
    private final ObservableList<CartTM> cartTMS = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LoadtDateTime();
        String nextId = null;
        try {
            nextId = generateNextOrderID();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        txtOrderID.setText(nextId);
        txtOrderID.setEditable(false);

        try {
            GetCustomersID();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        try {
            getItemIds();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        colitemcode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        coldescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colqtyonhand.setCellValueFactory(new PropertyValueFactory<>("qtyOnHand"));
        colcategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        colsize.setCellValueFactory(new PropertyValueFactory<>("size"));
        colunitprice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        coltotal.setCellValueFactory(new PropertyValueFactory<>("total"));

        cmdcustomerid.getSelectionModel().selectedItemProperty().addListener((observableValue, oldval, newval) -> {
            try {
                setValuesToCustomerText((String) newval);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        cmditemcode.getSelectionModel().selectedItemProperty().addListener((observableValue, oldval, newval) -> {
            try {
                setValuesToItems((String) newval);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private boolean validateStock(String itemCode, int requestedQty) throws SQLException {
        Products product = productService.searchProduct(itemCode);

        if (product == null) {
            showAlert(Alert.AlertType.ERROR, "Product Error", "Product not found!");
            return false;
        }

        int availableStock = product.getQty();

        if (availableStock <= 1) {
            showAlert(Alert.AlertType.ERROR, "Out of Stock",
                    "This item is out of stock!\n" +
                            "Product: " + product.getName() + "\n" +
                            "Available quantity: " + availableStock + "\n" +
                            "Please select a different item or restock this product.");
            return false;
        }

        if (requestedQty > availableStock) {
            showAlert(Alert.AlertType.WARNING, "Insufficient Stock",
                    "Requested quantity (" + requestedQty + ") exceeds available stock (" + availableStock + ")\n" +
                            "Please adjust the quantity.");
            return false;
        }

        if (availableStock <= 5) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Low Stock Warning");
            alert.setHeaderText("Low Stock Alert");
            alert.setContentText("Product '" + product.getName() + "' has low stock: " + availableStock + " items remaining.\n" +
                    "Consider restocking soon.");
            alert.showAndWait();
        }

        return true;
    }

    public String IdIncrement(String id) {
        if (id != null && id.length() >= 2 && id.startsWith("O")) {
            String numberPart = id.substring(1);
            try {
                int number = Integer.parseInt(numberPart);
                return String.format("O%03d", number + 1);
            } catch (NumberFormatException e) {
                return "O001";
            }
        }
        return "O001";
    }

    private String generateNextOrderID() throws SQLException {
        Connection connection = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            connection = DBConnection.getInstance().getConnection();
            stmt = connection.prepareStatement(
                    "SELECT id FROM orders ORDER BY id DESC LIMIT 1"
            );

            rs = stmt.executeQuery();
            if (rs.next()) {
                return IdIncrement(rs.getString("id"));
            } else {
                return "O001";
            }
        } finally {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (connection != null) connection.close();
        }
    }

    @FXML
    void btnaddtocart(ActionEvent event) {
        try {
            if (!validateCartInputs()) {
                return;
            }

            String itemcode = cmditemcode.getValue().toString();
            String itemname = txtdescription.getText();
            Integer qtyRequested = Integer.parseInt(txtqty.getText());
            Double unitprice = Double.parseDouble(txtunit.getText());
            String category = txtcategory.getText();
            String size = txtSize.getText();

            if (!validateStock(itemcode, qtyRequested)) {
                return;
            }

            Double total = qtyRequested * unitprice;

            CartTM cartItem = new CartTM(itemcode, itemname, qtyRequested, unitprice, category, size, total);
            cartTMS.add(cartItem);

            tblitems.setItems(FXCollections.observableArrayList(cartTMS));
            calNetTotal();

            clearItemFields();

        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid Input", "Please enter valid numbers for quantity and price!");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Database Error", "Error validating stock: " + e.getMessage());
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Unexpected Error", "An unexpected error occurred: " + e.getMessage());
        }
    }

    private boolean validateCartInputs() {
        if (cmditemcode.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Please select an item!");
            return false;
        }

        if (txtqty.getText().trim().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Please enter quantity!");
            return false;
        }

        try {
            int qty = Integer.parseInt(txtqty.getText());
            if (qty <= 0) {
                showAlert(Alert.AlertType.ERROR, "Validation Error", "Quantity must be greater than 0!");
                return false;
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Please enter a valid quantity!");
            return false;
        }

        return true;
    }

    private void clearItemFields() {
        cmditemcode.setValue(null);
        txtdescription.clear();
        txtqty.clear();
        txtSize.clear();
        txtstock.clear();
        txtunit.clear();
        txtcategory.clear();
    }

    @FXML
    void btnplaceorder(ActionEvent event) throws SQLException {
        try {
            if (!validateOrder()) {
                return;
            }

            String orderId = txtOrderID.getText();
            String date = lbldate.getText();
            String customerId = cmdcustomerid.getValue().toString();

            List<OrderDetails> orderDetails = new ArrayList<>();

            for (CartTM cartTM : cartTMS) {
                OrderDetails detail = new OrderDetails(
                        orderId,
                        cartTM.getItemCode(),
                        cartTM.getCategory(),
                        cartTM.getSize(),
                        cartTM.getQtyOnHand(),
                        cartTM.getUnitPrice(),
                        cartTM.getTotal()
                );
                orderDetails.add(detail);
            }

            Order order = new Order(orderId, date, customerId, orderDetails);
            Boolean success = orderService.placeOrder(order);

            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Success",
                        "Order placed successfully!\nOrder ID: " + orderId);
                clearForm();
                generateNewOrderID();
            }

        } catch (Exception e) {
            System.out.println("Exception in btnplaceorder: " + e.getMessage());
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Error placing order: " + e.getMessage());
        }
    }

    private boolean validateOrder() {
        if (cmdcustomerid.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Please select a customer!");
            return false;
        }

        if (cartTMS == null || cartTMS.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "Cart is empty! Add items before placing order.");
            return false;
        }

        return true;
    }

    private void generateNewOrderID() throws SQLException {
        String nextId = generateNextOrderID();
        txtOrderID.setText(nextId);
    }

    private void clearForm() {
        cmdcustomerid.setValue(null);
        customername.clear();
        txtaddress.clear();

        if (cartTMS != null) {
            cartTMS.clear();
        }

        if (tblitems != null) {
            tblitems.setItems(FXCollections.observableArrayList());
        }

        lbltotal.setText("0.00");
        clearItemFields();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void LoadtDateTime() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        lbldate.setText(format.format(date));

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, e -> {
                    LocalTime now = LocalTime.now();
                    lbltime.setText(String.format("%02d:%02d:%02d",
                            now.getHour(), now.getMinute(), now.getSecond()));
                }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void GetCustomersID() throws SQLException {
        List<String> customerIds = employeeService.getCustomerIds();
        cmdcustomerid.setItems(FXCollections.observableArrayList(customerIds));
    }

    private void setValuesToCustomerText(String Customerid) throws SQLException {
        Employee employee = employeeService.search(Customerid);
        if (employee != null) {
            customername.setText(employee.getName());
            txtaddress.setText(employee.getAddress());
        }
    }

    private void getItemIds() throws SQLException {
        List<String> Itemsid = productService.getItemsID();
        cmditemcode.setItems(FXCollections.observableArrayList(Itemsid));
    }

    private void setValuesToItems(String Itemsid) throws SQLException {
        Products products = productService.searchProduct(Itemsid);
        if (products != null) {
            txtdescription.setText(products.getName());
            txtSize.setText(products.getSize());
            txtstock.setText(products.getQty().toString());
            txtunit.setText(products.getPrice().toString());
            txtcategory.setText(products.getCategory());

            int stock = products.getQty();
            if (stock <= 1) {
                txtstock.setStyle("-fx-background-color: #ffcccc;");
                showAlert(Alert.AlertType.ERROR, "Out of Stock",
                        "This item is out of stock!\n" +
                                "Product: " + products.getName() + "\n" +
                                "Available quantity: " + stock + "\n" +
                                "Please select a different item.");
            } else if (stock <= 5) {
                txtstock.setStyle("-fx-background-color: #fff3cd;");
            } else {
                txtstock.setStyle("-fx-background-color: #d4edda;");
            }
        }
    }

    private void calNetTotal() {
        Double netTotal = 0.0;
        for (CartTM item : cartTMS) {
            if (item != null && item.getTotal() != null) {
                netTotal += item.getTotal();
            }
        }
        lbltotal.setText(String.format("%.2f", netTotal));
    }
}