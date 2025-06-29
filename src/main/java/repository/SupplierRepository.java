package repository;

import entity.SupplierEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class SupplierRepository extends BaseRepository<SupplierEntity, String> {
    
    public SupplierRepository() {
        super(SupplierEntity.class);
    }
    
    public Optional<SupplierEntity> findByEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<SupplierEntity> query = session.createQuery(
                "FROM SupplierEntity WHERE email = :email", SupplierEntity.class);
            query.setParameter("email", email);
            return query.uniqueResultOptional();
        } catch (Exception e) {
            throw new RuntimeException("Error finding supplier by email: " + e.getMessage(), e);
        }
    }
    
    public List<SupplierEntity> findByCompanyNameContaining(String companyName) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<SupplierEntity> query = session.createQuery(
                "FROM SupplierEntity WHERE companyName LIKE :companyName", SupplierEntity.class);
            query.setParameter("companyName", "%" + companyName + "%");
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding suppliers by company name: " + e.getMessage(), e);
        }
    }
    
    public List<String> getAllSupplierIds() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<String> query = session.createQuery(
                "SELECT supplierId FROM SupplierEntity ORDER BY supplierId", String.class);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error getting supplier IDs: " + e.getMessage(), e);
        }
    }
}