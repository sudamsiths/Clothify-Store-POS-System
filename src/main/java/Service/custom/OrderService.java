package Service.custom;

import DTO.Order;
import Service.SuperService;

import java.sql.SQLException;

public interface OrderService extends SuperService {
    Boolean placeOrder(Order order) throws SQLException;
}
