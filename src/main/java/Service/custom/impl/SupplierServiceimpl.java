package Service.custom.impl;

import DTO.Supplier;
import Service.ServiceFactory;
import Service.custom.ProductService;
import Service.custom.SupplierService;
import db.DBConnection;
import util.CRUDutil;
import util.ServiceType;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class SupplierServiceimpl implements SupplierService {


    @Override
    public Boolean addSupplier(Supplier supplier) throws SQLException {
        return CRUDutil.execute("INSERT INTO suppliers (supplier_id, supplier_name, company_name, email, item) VALUES (?,?,?,?,?)",
                supplier.getId(),supplier.getName(),supplier.getCompany_name(),supplier.getEmail(),supplier.getItem());
    }

    @Override
    public Boolean updateSupplier(Supplier supplier) {
        return null;
    }

    @Override
    public List<Supplier> getAllSuppliers() {
        return List.of();
    }

    @Override
    public List<Supplier> deletesuppliers(String id) {
        return List.of();
    }
}
