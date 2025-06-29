package service;

import DTO.Order;
import DTO.OrderDetails;
import entity.OrderEntity;
import entity.OrderDetailEntity;
import repository.OrderRepository;
import repository.ProductRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.time.LocalDate;
import java.util.List;

public class OrderService {
    
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    
    public OrderService() {
        this.orderRepository = new OrderRepository();
        this.productRepository = new ProductRepository();
    }
    
    public boolean placeOrder(Order order) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            
            // Create order entity
            OrderEntity orderEntity = new OrderEntity();
            orderEntity.setId(order.getId());
            orderEntity.setOrderDate(LocalDate.now());
            orderEntity.setCustomerId(order.getCustomer_id());
            
            // Calculate total amount
            double totalAmount = 0.0;
            for (OrderDetails detail : order.getOrderDetails()) {
                totalAmount += detail.getQty() * detail.getUnit_price();
            }
            orderEntity.setTotalAmount(totalAmount);
            
            // Save order
            session.save(orderEntity);
            
            // Process order details and update stock
            for (OrderDetails detail : order.getOrderDetails()) {
                // Create order detail entity
                OrderDetailEntity detailEntity = new OrderDetailEntity();
                detailEntity.setOrderId(order.getId());
                detailEntity.setProductId(detail.getItem_code());
                detailEntity.setQuantity(detail.getQty());
                detailEntity.setUnitPrice(detail.getUnit_price());
                detailEntity.setTotalPrice(detail.getQty() * detail.getUnit_price());
                
                session.save(detailEntity);
                
                // Update product stock
                boolean stockUpdated = productRepository.updateStock(detail.getItem_code(), detail.getQty());
                if (!stockUpdated) {
                    throw new RuntimeException("Insufficient stock for product: " + detail.getItem_code());
                }
            }
            
            transaction.commit();
            return true;
            
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Error placing order: " + e.getMessage());
            return false;
        }
    }
    
    public List<OrderEntity> getOrdersByCustomer(String customerId) {
        return orderRepository.findByCustomerId(customerId);
    }
    
    public List<OrderEntity> getOrdersByDateRange(LocalDate startDate, LocalDate endDate) {
        return orderRepository.findByDateRange(startDate, endDate);
    }
    
    public Double getTotalSalesByDate(LocalDate date) {
        return orderRepository.getTotalSalesByDate(date);
    }
    
    public long getOrderCount() {
        return orderRepository.count();
    }
}