package Service.custom.impl;

import DTO.Employee;
import DTO.Supplier;
import Entity.SupplierEntity;
import Service.custom.SupplierService;
import org.modelmapper.ModelMapper;
import repository.DAOFactory;
import repository.custom.SupplierRepository;
import util.CRUDutil;
import util.RepositoryType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SupplierServiceimpl implements SupplierService {

    SupplierRepository supplierRepository= DAOFactory.getInstance().getRepositoryType(RepositoryType.Supplier);

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
        SupplierEntity supplier1 =new ModelMapper().map(supplier ,SupplierEntity.class);
        return supplierRepository.add(supplier1);
    }

    @Override
    public Boolean updateSupplier(Supplier supplier) {
        return null;
    }

    @Override
    public List<Supplier> getAllSuppliers() throws SQLException {
        List<SupplierEntity> all = supplierRepository.getAll();
        List<Supplier> suppliers = new ArrayList<>();
        for (SupplierEntity supplierEntity : all) {
            Supplier supplier = new ModelMapper().map(supplierEntity, Supplier.class);
            suppliers.add(supplier);
        }
        return suppliers;
    }

    @Override
    public List<Supplier> deletesuppliers(String id) {
        return List.of();
    }

    @Override
    public Supplier searchById(String supplierId) throws SQLException {
        SupplierEntity supplierEntity = supplierRepository.searchById(supplierId);
        if (supplierEntity != null) {
            return new ModelMapper().map(supplierEntity, Supplier.class);
        }
        return null;
    }
}
