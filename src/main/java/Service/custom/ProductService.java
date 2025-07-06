package Service.custom;

import DTO.Products;
import Service.SuperService;

import java.sql.SQLException;
import java.util.List;

public interface ProductService extends SuperService {
    boolean addProduct(Products products) throws SQLException;

    boolean updateProduct(Products products) throws SQLException;

    Products searchProduct(String search) throws SQLException;

    List<Products> getAllProduct() throws SQLException;

    List<Products> deleteProducts(String id);

    List<String> getItemsID() throws SQLException;
}
