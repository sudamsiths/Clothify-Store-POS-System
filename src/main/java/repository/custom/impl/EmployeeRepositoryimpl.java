package repository.custom.impl;

import Entity.EmployeeEntity;
import repository.custom.EmployeeRepository;
import util.CRUDutil;

import java.sql.SQLException;
import java.util.List;

public class EmployeeRepositoryimpl implements EmployeeRepository {


    @Override
    public boolean add(EmployeeEntity entity) {
        return false;
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
    public List<EmployeeEntity> getAll() {
        return List.of();
    }
}
