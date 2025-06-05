//package repository.custom.impl;
//
//import Entity.EmployeeEntity;
//import repository.custom.EmployeeRepository;
//import util.CRUDutil;
//
//import java.sql.SQLException;
//import java.util.List;
//
//public class EmployeeRepositoryimpl implements EmployeeRepository {
//
//    @Override
//    public boolean add(Object entity) throws SQLException {
//        try {
//            EmployeeEntity employee = (EmployeeEntity) entity;
//
//            String sql = "INSERT INTO customer VALUES (?, ?, ?, ?, ?, ?)";
//
//            boolean isAdded = CRUDutil.execute(
//                    sql,
//                    employee.getId(),
//                    employee.getName(),
//                    employee.getNic(),
//                    employee.getAddress(),
//                    employee.getDOB(),
//                    employee.getContactno()
//            );
//
//            return isAdded;
//
//        } finally {
//
//        }
//    }
//
//    @Override
//    public boolean update(Object entity) {
//        return false;
//    }
//
//    @Override
//    public boolean deleteById(Object o) {
//        return false;
//    }
//
//    @Override
//    public Object searchById(Object o) {
//        return null;
//    }
//
//    @Override
//    public List getAll() {
//        return List.of();
//    }
//}
