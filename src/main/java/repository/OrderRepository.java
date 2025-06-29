package repository;

import entity.OrderEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.time.LocalDate;
import java.util.List;

public class OrderRepository extends BaseRepository<OrderEntity, String> {
    
    public OrderRepository() {
        super(OrderEntity.class);
    }
    
    public List<OrderEntity> findByCustomerId(String customerId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<OrderEntity> query = session.createQuery(
                "FROM OrderEntity WHERE customerId = :customerId ORDER BY orderDate DESC", OrderEntity.class);
            query.setParameter("customerId", customerId);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding orders by customer: " + e.getMessage(), e);
        }
    }
    
    public List<OrderEntity> findByDateRange(LocalDate startDate, LocalDate endDate) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<OrderEntity> query = session.createQuery(
                "FROM OrderEntity WHERE orderDate BETWEEN :startDate AND :endDate ORDER BY orderDate DESC", OrderEntity.class);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding orders by date range: " + e.getMessage(), e);
        }
    }
    
    public Double getTotalSalesByDate(LocalDate date) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Double> query = session.createQuery(
                "SELECT COALESCE(SUM(totalAmount), 0.0) FROM OrderEntity WHERE orderDate = :date", Double.class);
            query.setParameter("date", date);
            return query.uniqueResult();
        } catch (Exception e) {
            throw new RuntimeException("Error getting total sales by date: " + e.getMessage(), e);
        }
    }
}