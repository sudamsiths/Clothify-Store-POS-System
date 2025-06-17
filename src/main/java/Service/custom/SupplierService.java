package Service.custom;

import DTO.Employee;
import DTO.Supplier;
import DTO.employeeuser;
import Service.SuperService;

import java.sql.SQLException;
import java.util.List;

public interface SupplierService extends SuperService {


    List<String> getCustomerIds() throws SQLException;

    Boolean addSupplier(Supplier supplier) throws SQLException;
    Boolean updateSupplier(Supplier supplier);
    List<Supplier> getAllSuppliers() throws SQLException;
    List<Supplier>deletesuppliers(String id);

    Supplier searchById(String supplierId) throws SQLException;
}
