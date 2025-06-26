package repository.custom.impl;

import Entity.EmployeeEntity;
import Entity.SupplierEntity;
import db.DBConnection;
import repository.custom.SupplierRepository;

import java.sql.SQLException;
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
    public SupplierEntity searchById(String s) {
        return null;
    }

    @Override
    public List<SupplierEntity> getAll(EmployeeEntity entity) throws SQLException {
        return List.of();
    }
}
