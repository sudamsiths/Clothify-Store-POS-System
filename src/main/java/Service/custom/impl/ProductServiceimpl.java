package Service.custom.impl;

import DTO.Products;
import Service.custom.ProductService;
import util.CRUDutil;

import java.sql.SQLException;
import java.util.List;

public class ProductServiceimpl implements ProductService {

    @Override
    public boolean addProduct(Products products) throws SQLException {
        return CRUDutil.execute(
                "INSERT INTO products (id, supplier_id, name, category, size, price, qty, image_url) VALUES (?,?,?,?,?,?,?,?)",
                products.getId(),
                products.getSupplier_id(),
                products.getName(),
                products.getCategory(),
                products.getSize(),
                products.getPrice(),
                products.getQty(),
                products.getImage_url()
        );
    }

    @Override
    public boolean updateProduct(Products products) {
        return false;
    }

    @Override
    public Products searchProduct(String id) {
        return null;
    }

    @Override
    public List<Products> getAllProduct() {
        return List.of();
    }

    @Override
    public List<Products> deleteProducts(String id) {
        return List.of();
    }
}
