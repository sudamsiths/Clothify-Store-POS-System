package Service.custom;

import DTO.Employee;
import DTO.Products;
import Service.SuperService;
import Service.custom.impl.ProductServiceimpl;

import java.sql.SQLException;
import java.util.List;

public interface ProductService extends SuperService {
    boolean addProduct(Products products) throws SQLException;
    boolean updateProduct(Products products);
    Products searchProduct(String id);
    List<Products> getAllProduct();
    List<Products>deleteProducts(String id);

}
