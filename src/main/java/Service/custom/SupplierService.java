package Service.custom;

import DTO.Employee;
import DTO.Supplier;
import DTO.employeeuser;
import Service.SuperService;

import java.sql.SQLException;
import java.util.List;

public interface SupplierService extends SuperService {
    Boolean addSupplier(Supplier supplier) throws SQLException;
    Boolean updateSupplier(Supplier supplier);
    List<Supplier> getAllSuppliers();
    List<Supplier>deletesuppliers(String id);
}
