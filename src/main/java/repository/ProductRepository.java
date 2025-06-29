package repository;

import entity.ProductEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class ProductRepository extends BaseRepository<ProductEntity, String> {
    
    public ProductRepository() {
        super(ProductEntity.class);
    }
    
    public List<ProductEntity> findByCategory(String category) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<ProductEntity> query = session.createQuery(
                "FROM ProductEntity WHERE category = :category", ProductEntity.class);
            query.setParameter("category", category);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding products by category: " + e.getMessage(), e);
        }
    }
    
    public List<ProductEntity> findByNameContaining(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<ProductEntity> query = session.createQuery(
                "FROM ProductEntity WHERE name LIKE :name", ProductEntity.class);
            query.setParameter("name", "%" + name + "%");
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding products by name: " + e.getMessage(), e);
        }
    }
    
    public List<ProductEntity> findBySupplierId(String supplierId) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<ProductEntity> query = session.createQuery(
                "FROM ProductEntity WHERE supplierId = :supplierId", ProductEntity.class);
            query.setParameter("supplierId", supplierId);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding products by supplier: " + e.getMessage(), e);
        }
    }
    
    public boolean updateStock(String productId, int quantity) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            
            Query query = session.createQuery(
                "UPDATE ProductEntity SET qty = qty - :quantity WHERE id = :productId AND qty >= :quantity");
            query.setParameter("quantity", quantity);
            query.setParameter("productId", productId);
            
            int updatedRows = query.executeUpdate();
            transaction.commit();
            
            return updatedRows > 0;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Error updating stock: " + e.getMessage(), e);
        }
    }
    
    public List<String> getAllProductIds() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<String> query = session.createQuery(
                "SELECT id FROM ProductEntity ORDER BY id", String.class);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error getting product IDs: " + e.getMessage(), e);
        }
    }
}