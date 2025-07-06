package Service.custom.impl;

import DTO.Order;
import Service.custom.OrderService;
import db.DBConnection;
import javafx.scene.control.Alert;
import repository.DAOFactory;
import repository.custom.OrderRepository;
import util.RepositoryType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderServiceImpl implements OrderService {

    OrderRepository orderRepository = DAOFactory.getInstance().getRepositoryType(RepositoryType.Order);

    @Override
    public Boolean placeOrder(Order order) throws SQLException {
        String sql = "INSERT INTO orders VALUES(?,?,?)";
        try (Connection connection = DBConnection.getInstance().getConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement psTm = connection.prepareStatement(sql);
            psTm.setObject(1, order.getId());
            psTm.setObject(2, order.getDate());
            psTm.setObject(3, order.getCustomerId());
            boolean isOrderAdded = psTm.executeUpdate() > 0;

            if (isOrderAdded) {
                boolean isOrderDetailAdded = new OrderDetailServiceImpl().addOrderDetails(order.getOrderDetails());
                if (isOrderDetailAdded) {
                    boolean isStockUpdated = new ProductServiceimpl().updateStock(order.getOrderDetails());
                    if (isStockUpdated) {
                        connection.commit();
                        new Alert(Alert.AlertType.INFORMATION, "Order Placed!!").show();
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
