package repository.custom.impl;

import Entity.ProductEntity;
import repository.custom.ProductRepository;
import util.CRUDutil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
    public boolean update(ProductEntity entity) throws SQLException {
        String sql = "UPDATE products SET supplier_id=?, name=?, category=?, size=?, price=?, qty=? WHERE id=?";

        Boolean result = CRUDutil.execute(sql,
                entity.getSupplier_id(),
                entity.getName(),
                entity.getCategory(),
                entity.getSize(),
                entity.getPrice(),
                entity.getQty(),
                entity.getId()
        );

        return result;
    }

    @Override
    public boolean deleteById(String s) {
        return false;
    }

    @Override
    public ProductEntity searchById(String s) throws SQLException {
        ResultSet resultSet = CRUDutil.execute("SELECT * FROM products WHERE id = ?", s);
        if (resultSet.next()) {
            return new ProductEntity(
                    resultSet.getString("id"),
                    resultSet.getString("supplier_id"),
                    resultSet.getString("name"),
                    resultSet.getString("category"),
                    resultSet.getString("size"),
                    resultSet.getDouble("price"),
                    resultSet.getInt("qty"),
                    resultSet.getString("image_url")
            );
        }
        return null;
    }

    @Override
    public List<ProductEntity> getAll() throws SQLException {
        ResultSet resultSet = CRUDutil.execute("SELECT * FROM products");
        List<ProductEntity> productEntities = new ArrayList<>();
        while (resultSet.next()) {
            productEntities.add(new ProductEntity(
                    resultSet.getString("id"),
                    resultSet.getString("supplier_id"),
                    resultSet.getString("name"),
                    resultSet.getString("category"),
                    resultSet.getString("size"),
                    resultSet.getDouble("price"),
                    resultSet.getInt("qty"),
                    resultSet.getString("image_url")
            ));
        }
        return productEntities;
    }
}
