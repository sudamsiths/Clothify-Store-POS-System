package repository.custom.impl;

import Entity.EmployeeEntity;
import Entity.ProductEntity;
import repository.custom.ProductRepository;
import util.CRUDutil;

import java.sql.SQLException;
import java.util.List;

public class ProductRepositoryimpl implements ProductRepository {
    @Override
    public boolean add(ProductEntity entity) throws SQLException {
        return CRUDutil.execute(
                "INSERT INTO products (id, supplier_id, name, category, size, price, qty, image_url) VALUES (?,?,?,?,?,?,?,?)",
                entity.getId(),
                entity.getSupplier_id(),
                entity.getName(),
                entity.getCategory(),
                entity.getSize(),
                entity.getPrice(),
                entity.getQty(),
                entity.getImage_url()
        );
    }

    @Override
    public boolean update(ProductEntity entity) {
        return false;
    }

    @Override
    public boolean deleteById(String s) {
        return false;
    }

    @Override
    public ProductEntity searchById(String s) {
        return null;
    }

    @Override
    public List<ProductEntity> getAll(EmployeeEntity entity) throws SQLException {
        return List.of();
    }
}
