package service;

import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

import java.util.function.Function;
import java.util.function.Supplier;

public class TransactionService {
    
    public static <T> T executeInTransaction(Function<Session, T> operation) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            T result = operation.apply(session);
            transaction.commit();
            return result;
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Transaction failed: " + e.getMessage(), e);
        }
    }
    
    public static void executeInTransaction(java.util.function.Consumer<Session> operation) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            operation.accept(session);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new RuntimeException("Transaction failed: " + e.getMessage(), e);
        }
    }
    
    public static <T> T executeWithoutTransaction(Function<Session, T> operation) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return operation.apply(session);
        } catch (Exception e) {
            throw new RuntimeException("Operation failed: " + e.getMessage(), e);
        }
    }
}