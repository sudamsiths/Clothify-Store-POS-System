package repository.custom.impl;

import Entity.SupplierEntity;
import db.DBConnection;
import org.hibernate.HibernateError;
import org.hibernate.Session;
import org.hibernate.Transaction;
import repository.custom.SupplierRepository;
import util.CRUDutil;
import util.HibernateUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierRepositoryimpl implements SupplierRepository {


    @Override
    public boolean add(SupplierEntity entity) throws SQLException {

        System.out.println(entity);

        Transaction transaction = null;

        try(Session session =HibernateUtil.getSessionFactory().getCurrentSession()) {

            transaction = session.beginTransaction();
            session.persist(entity);

            transaction.commit();


        } catch (HibernateError e) {

            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }

        return true;
    }

    @Override
    public boolean update(SupplierEntity entity) {
        return false;
    }

    @Override
    public boolean deleteById(String s) {
        return false;
    }

    @Override
    public SupplierEntity searchById(String s) throws SQLException {
        ResultSet resultSet = CRUDutil.execute("SELECT * FROM suppliers WHERE supplier_id =?", s);
        if (resultSet.next()) {
            return new SupplierEntity(
                    resultSet.getString("supplier_id"),
                    resultSet.getString("supplier_name"),
                    resultSet.getString("company_name"),
                    resultSet.getString("email"),
                    resultSet.getString("item")
            );
        }
        return null;
    }

    @Override
    public List<SupplierEntity> getAll() throws SQLException {
        String sql = "SELECT * FROM suppliers";
        List<SupplierEntity> suppliers = new ArrayList<>();
        try (var connection = DBConnection.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                SupplierEntity supplier = new SupplierEntity(
                        resultSet.getString("supplier_id"),
                        resultSet.getString("supplier_name"),
                        resultSet.getString("company_name"),
                        resultSet.getString("email"),
                        resultSet.getString("item")
                );
                suppliers.add(supplier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return suppliers;
    }
}
