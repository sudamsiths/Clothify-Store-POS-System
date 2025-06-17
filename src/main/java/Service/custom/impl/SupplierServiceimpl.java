package Service.custom.impl;

import DTO.Employee;
import DTO.Supplier;
import Service.custom.SupplierService;
import util.CRUDutil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierServiceimpl implements SupplierService {

    @Override
    public List<String> getCustomerIds() throws SQLException {
        List<Supplier> all = getAllSuppliers();
        ArrayList<String> supplierIdList = new ArrayList<>();
        all.forEach(supplier -> {
            supplierIdList.add(supplier.getSupplier_id());
        });
        return supplierIdList;
    }

    @Override
    public Boolean addSupplier(Supplier supplier) throws SQLException {
        return CRUDutil.execute("INSERT INTO suppliers (supplier_id, supplier_name, company_name, email, item) VALUES (?,?,?,?,?)",
                supplier.getSupplier_id(), supplier.getSupplier_name(), supplier.getCompany_name(), supplier.getEmail(), supplier.getItem());
    }

    @Override
    public Boolean updateSupplier(Supplier supplier) {
        return null;
    }

    @Override
    public List<Supplier> getAllSuppliers() throws SQLException {
        List<Supplier> supplierList = new ArrayList<>();

        try (ResultSet resultSet = CRUDutil.execute("select * from suppliers")) {
            while (resultSet.next()) {
                Supplier supplier = new Supplier();
                supplier.setSupplier_id(resultSet.getString("supplier_id"));
                // Fixed: Use correct field name
                supplier.setSupplier_name(resultSet.getString("supplier_name"));
                supplier.setCompany_name(resultSet.getString("company_name"));
                supplier.setEmail(resultSet.getString("email"));
                supplier.setItem(resultSet.getString("item"));

                supplierList.add(supplier);
            }
        }
        return supplierList;
    }

    @Override
    public List<Supplier> deletesuppliers(String id) {
        return List.of();
    }

    @Override
    public Supplier searchById(String supplierId) throws SQLException {
        ResultSet resultSet = CRUDutil.execute("SELECT * FROM suppliers WHERE supplier_id =?", supplierId);
        if (resultSet.next()) {
            return new Supplier(
                    resultSet.getString("supplier_id"),
                    resultSet.getString("supplier_name"),
                    resultSet.getString("company_name"),
                    resultSet.getString("email"),
                    resultSet.getString("item")
            );
        }
        return null;
    }
}
