package repository.custom.impl;

import DTO.employeeuser;
import Entity.EmployeeEntity;
import repository.custom.EmployeeRepository;
import util.CRUDutil;

import java.sql.SQLException;
import java.util.List;

public class EmployeeRepositoryimpl implements EmployeeRepository {


    @Override
    public boolean add(EmployeeEntity entity) throws SQLException {
        return CRUDutil.execute("INSERT INTO customer VALUES (?,?,?,?,?,?)",
                entity.getId(),
                entity.getName(),
                entity.getNic(),
                entity.getAddress(),
                entity.getDOB(),
                entity.getContactno()
        );
    }

    @Override
    public boolean update(EmployeeEntity entity) {
        return false;
    }

    @Override
    public boolean deleteById(String s) {
        return false;
    }

    @Override
    public EmployeeEntity searchById(String s) {
        return null;
    }

    @Override
    public List<EmployeeEntity> getAll() throws SQLException {
        return List.of();
    }

    @Override
    public Boolean adduser(employeeuser employee) {
        try {
            return CRUDutil.execute("INSERT INTO user VALUES (?,?)",
                    employee.getEmail(),
                    employee.getPassword()
            );
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
