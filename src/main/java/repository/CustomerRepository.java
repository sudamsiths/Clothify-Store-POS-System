package repository;

import entity.CustomerEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class CustomerRepository extends BaseRepository<CustomerEntity, String> {
    
    public CustomerRepository() {
        super(CustomerEntity.class);
    }
    
    public Optional<CustomerEntity> findByNic(String nic) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<CustomerEntity> query = session.createQuery(
                "FROM CustomerEntity WHERE nic = :nic", CustomerEntity.class);
            query.setParameter("nic", nic);
            return query.uniqueResultOptional();
        } catch (Exception e) {
            throw new RuntimeException("Error finding customer by NIC: " + e.getMessage(), e);
        }
    }
    
    public List<CustomerEntity> findByNameContaining(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<CustomerEntity> query = session.createQuery(
                "FROM CustomerEntity WHERE name LIKE :name", CustomerEntity.class);
            query.setParameter("name", "%" + name + "%");
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error finding customers by name: " + e.getMessage(), e);
        }
    }
    
    public List<String> getAllCustomerIds() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<String> query = session.createQuery(
                "SELECT id FROM CustomerEntity ORDER BY id", String.class);
            return query.list();
        } catch (Exception e) {
            throw new RuntimeException("Error getting customer IDs: " + e.getMessage(), e);
        }
    }
}