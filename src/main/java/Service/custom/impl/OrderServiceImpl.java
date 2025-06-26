package Service.custom.impl;

import DTO.Order;
import Service.custom.OrderService;
import db.DBConnection;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderServiceImpl implements OrderService {

    @Override
    public Boolean placeOrder(Order order) throws SQLException {
        String sql = "INSERT INTO orders VALUES(?,?,?)";
         Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement psTm = connection.prepareStatement(sql);
            connection.setAutoCommit(false);
            psTm.setObject(1, order.getId());
            psTm.setObject(2, order.getDate());
            psTm.setObject(3, order.getCustomer_id());
            Boolean isOrderAdd = psTm.executeUpdate() > 0;
            if (isOrderAdd) {
                Boolean isOrderDetailAdd = new OrderDetailServiceImpl().addOrderDetails(order.getOrderDetails());
                if (isOrderDetailAdd) {
                    Boolean isUpdateStock = new ProductServiceimpl().updateStock(order.getOrderDetails());
                    if (isUpdateStock) {
                        connection.commit();
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;
    }
}
