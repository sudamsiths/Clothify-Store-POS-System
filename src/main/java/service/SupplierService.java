package service;

import DTO.Supplier;
import entity.SupplierEntity;
import repository.SupplierRepository;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SupplierService {
    
    private final SupplierRepository supplierRepository;
    private final ModelMapper modelMapper;
    
    public SupplierService() {
        this.supplierRepository = new SupplierRepository();
        this.modelMapper = new ModelMapper();
    }
    
    public boolean addSupplier(Supplier supplier) {
        try {
            SupplierEntity entity = modelMapper.map(supplier, SupplierEntity.class);
            entity.setSupplierId(supplier.getSupplier_id());
            entity.setSupplierName(supplier.getSupplier_name());
            entity.setCompanyName(supplier.getCompany_name());
            supplierRepository.save(entity);
            return true;
        } catch (Exception e) {
            System.err.println("Error adding supplier: " + e.getMessage());
            return false;
        }
    }
    
    public boolean updateSupplier(Supplier supplier) {
        try {
            SupplierEntity entity = modelMapper.map(supplier, SupplierEntity.class);
            entity.setSupplierId(supplier.getSupplier_id());
            entity.setSupplierName(supplier.getSupplier_name());
            entity.setCompanyName(supplier.getCompany_name());
            supplierRepository.save(entity);
            return true;
        } catch (Exception e) {
            System.err.println("Error updating supplier: " + e.getMessage());
            return false;
        }
    }
    
    public Supplier findSupplierById(String id) {
        Optional<SupplierEntity> entity = supplierRepository.findById(id);
        return entity.map(this::mapToDTO).orElse(null);
    }
    
    public Supplier findSupplierByEmail(String email) {
        Optional<SupplierEntity> entity = supplierRepository.findByEmail(email);
        return entity.map(this::mapToDTO).orElse(null);
    }
    
    public List<Supplier> getAllSuppliers() {
        List<SupplierEntity> entities = supplierRepository.findAll();
        return entities.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    public List<Supplier> searchSuppliersByCompanyName(String companyName) {
        List<SupplierEntity> entities = supplierRepository.findByCompanyNameContaining(companyName);
        return entities.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    public boolean deleteSupplier(String id) {
        try {
            supplierRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            System.err.println("Error deleting supplier: " + e.getMessage());
            return false;
        }
    }
    
    public List<String> getAllSupplierIds() {
        return supplierRepository.getAllSupplierIds();
    }
    
    public long getSupplierCount() {
        return supplierRepository.count();
    }
    
    private Supplier mapToDTO(SupplierEntity entity) {
        Supplier dto = modelMapper.map(entity, Supplier.class);
        dto.setSupplier_id(entity.getSupplierId());
        dto.setSupplier_name(entity.getSupplierName());
        dto.setCompany_name(entity.getCompanyName());
        return dto;
    }
}