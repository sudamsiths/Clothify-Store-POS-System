package repository.custom;

import DTO.Order;
import Entity.OrderEntity;
import repository.CRUDRepository;

public interface OrderRepository extends CRUDRepository<OrderEntity, String> {
    boolean placeOrder(Order orderEntity) throws Exception;
}
