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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    OrderService orderService =ServiceFactory.getInstance().getServiceType(ServiceType.Order);
    List<CartTM>cartTMS =new ArrayList<>();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        LoadtDateTime();
        String nextId = null;
        try {
            nextId = generateNextCustomerID();
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
    public String IdIncrement(String id) {
        if (id != null && id.length() >= 2 && id.startsWith("O")) {
            String numberPart = id.substring(1);
            int number = Integer.parseInt(numberPart);
            return String.format("O%03d", number + 1);
        }
        return "O001";
    }

    private String generateNextCustomerID() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement stmt = connection.prepareStatement(
                "SELECT id FROM orders ORDER BY id DESC LIMIT 1"
        );

        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return IdIncrement(rs.getString("id"));
        } else {
            return "O001";
        }
    }

    @FXML
    void btnaddtocart(ActionEvent event) {
        String itemcode=cmditemcode.getValue().toString();
        String itemname=txtdescription.getText();
        Integer qtyonhand= Integer.parseInt(txtqty.getText());
        Double unitprice=Double.parseDouble(txtunit.getText());
        String category=txtcategory.getText();
        String size=txtSize.getText();
        Double total=qtyonhand*unitprice;

        cartTMS.add( new CartTM(itemcode,itemname,qtyonhand,unitprice,category,size,total));
        tblitems.setItems(FXCollections.observableArrayList(cartTMS));
        calNetTotal();
    }

    @FXML
    void btnplaceorder(ActionEvent event) {
        String orderId = txtOrderID.getText();
        String date = lbldate.getText();
        String customerId = cmdcustomerid.getValue().toString();
        List<OrderDetails> orderDetails = new ArrayList<>();

        cartTMS.forEach(cartTM -> {
            orderDetails.add(
                    new OrderDetails(
                            orderId,
                            cartTM.getItemCode(),
                            cartTM.getQtyOnHand(),
                            cartTM.getUnitPrice()
                    )
            );
        });
        Order order = new Order(orderId, date, customerId, orderDetails);
        System.out.println(order);
        orderService.placeOrder(order);
    }

    private void LoadtDateTime(){
        Date date =new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        lbldate.setText(format.format(date));

        Timeline timeline=new Timeline(
                new KeyFrame(Duration.ZERO, e->{
                    LocalTime now = LocalTime.now();
                    lbltime.setText(now.getHour()+" : "+now.getMinute()+" : "+now.getSecond());
                }),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void GetCustomersID() throws SQLException {
        List<String> customerIds= employeeService.getCustomerIds();
        cmdcustomerid.setItems(FXCollections.observableArrayList(customerIds));
    }

    private void setValuesToCustomerText(String Customerid) throws SQLException {
        Employee employee = employeeService.search(Customerid);
        customername.setText(employee.getName());
        txtaddress.setText(employee.getAddress());
    }

    private void getItemIds() throws SQLException {
        List<String>Itemsid= productService.getItemsID();
        cmditemcode.setItems(FXCollections.observableArrayList(Itemsid));
    }

    private void setValuesToItems(String Itemsid) throws SQLException {

        Products products = productService.searchProduct(Itemsid);
        txtdescription.setText(products.getName());
        txtSize.setText(products.getSize());
        txtstock.setText(products.getQty().toString());
        txtunit.setText(products.getPrice().toString());
        txtcategory.setText(products.getCategory());
    }


    private void calNetTotal(){
        Double netTotal = 0.0;
        for (CartTM item : cartTMS ){
            netTotal+=item.getTotal();
        }

        lbltotal.setText(netTotal.toString());

    }
}
