package repository.custom.impl;

import DTO.Order;
import Entity.OrderEntity;
import Service.custom.impl.OrderDetailServiceImpl;
import Service.custom.impl.ProductServiceimpl;
import db.DBConnection;
import javafx.scene.control.Alert;
import repository.custom.OrderRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderRepositoryimpl implements OrderRepository {

    @Override
    public boolean add(OrderEntity entity) throws SQLException {
        return false;
    }

    @Override
    public boolean update(OrderEntity entity) {
        return false;
    }

    @Override
    public boolean deleteById(String s) {
        return false;
    }

    @Override
    public OrderEntity searchById(String s) {
        return null;
    }

    @Override
    public List<OrderEntity> getAll() throws SQLException {
        return List.of();
    }

    @Override
    public boolean placeOrder(Order orderEntity) throws Exception {
        String sql = "INSERT INTO orders VALUES(?,?,?)";
        try (Connection connection = DBConnection.getInstance().getConnection()) {
            connection.setAutoCommit(false);
            PreparedStatement psTm = connection.prepareStatement(sql);
            psTm.setObject(1, orderEntity.getId());
            psTm.setObject(2, orderEntity.getDate());
            psTm.setObject(3, orderEntity.getCustomerId());
            boolean isOrderAdded = psTm.executeUpdate() > 0;

            if (isOrderAdded) {
                boolean isOrderDetailAdded = new OrderDetailServiceImpl().addOrderDetails(orderEntity.getOrderDetails());
                if (isOrderDetailAdded) {
                    boolean isStockUpdated = new ProductServiceimpl().updateStock(orderEntity.getOrderDetails());
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
