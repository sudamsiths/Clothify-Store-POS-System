package repository.custom.impl;

import DTO.Supplier;
import Entity.SupplierEntity;
import db.DBConnection;
import repository.custom.SupplierRepository;
import util.CRUDutil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierRepositoryimpl implements SupplierRepository {


    @Override
    public boolean add(SupplierEntity entity) throws SQLException {
        String sql = "INSERT INTO suppliers VALUES (?, ?, ?, ?, ?)";
        try (var connection = DBConnection.getInstance().getConnection();
             var preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, entity.getSupplier_id());
            preparedStatement.setString(2, entity.getSupplier_name());
            preparedStatement.setString(3, entity.getCompany_name());
            preparedStatement.setString(4, entity.getEmail());
            preparedStatement.setString(5, entity.getItem());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
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
