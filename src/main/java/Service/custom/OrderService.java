package Service.custom;

import DTO.Order;
import Service.SuperService;

public interface OrderService extends SuperService {
    Boolean placeOrder(Order order);
}
