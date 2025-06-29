package repository;

import entity.UserEntity;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.Optional;

public class UserRepository extends BaseRepository<UserEntity, Long> {
    
    public UserRepository() {
        super(UserEntity.class);
    }
    
    public Optional<UserEntity> findByEmail(String email) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<UserEntity> query = session.createQuery(
                "FROM UserEntity WHERE email = :email AND isActive = true", UserEntity.class);
            query.setParameter("email", email);
            return query.uniqueResultOptional();
        } catch (Exception e) {
            throw new RuntimeException("Error finding user by email: " + e.getMessage(), e);
        }
    }
    
    public Optional<UserEntity> findByEmailAndPassword(String email, String password) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<UserEntity> query = session.createQuery(
                "FROM UserEntity WHERE email = :email AND password = :password AND isActive = true", UserEntity.class);
            query.setParameter("email", email);
            query.setParameter("password", password);
            return query.uniqueResultOptional();
        } catch (Exception e) {
            throw new RuntimeException("Error authenticating user: " + e.getMessage(), e);
        }
    }
}