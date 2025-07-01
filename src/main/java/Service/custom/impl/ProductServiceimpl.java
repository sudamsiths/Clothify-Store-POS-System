package Service.custom.impl;

import DTO.OrderDetails;
import DTO.Products;
import Entity.ProductEntity;
import Service.custom.ProductService;
import org.modelmapper.ModelMapper;
import repository.DAOFactory;
import repository.custom.ProductRepository;
import util.CRUDutil;
import util.RepositoryType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductServiceimpl implements ProductService {

    ProductRepository productRepository = DAOFactory.getInstance().getRepositoryType(RepositoryType.Product);

    @Override
    public boolean addProduct(Products products) throws SQLException {
        ProductEntity entity =new ModelMapper().map(products, ProductEntity.class);
        return productRepository.add(entity);
    }

    @Override
    public boolean updateProduct(Products products) throws SQLException {
        ProductEntity entity = new ModelMapper().map(products, ProductEntity.class);
        return productRepository.update(entity);
    }
    @Override
    public Products searchProduct(String search) throws SQLException {
        ProductEntity entity = productRepository.searchById(search);
        if (entity != null) {
            return new ModelMapper().map(entity, Products.class);
        }
        return null;
    }

    @Override
    public List<Products> getAllProduct() throws SQLException {
        List <ProductEntity> productEntities =  productRepository.getAll();
        List<Products> productsList = new ArrayList<>();
        for (ProductEntity entity : productEntities) {
            Products products = new ModelMapper().map(entity, Products.class);
            productsList.add(products);
        }
        return productsList;
    }


    @Override
    public List<Products> deleteProducts(String id) {
        return List.of();
    }


    @Override
    public List<String> getItemsID() throws SQLException {
        List<Products>products =getAllProduct();
        ArrayList<String> ItemsList = new ArrayList<>();
        products.forEach(item -> {
            ItemsList.add(item.getId());
        });
        return ItemsList;
    }
    public Boolean updateStock(List<OrderDetails> orderDetails) throws SQLException {
        for (OrderDetails details : orderDetails) {
            Boolean isUpdate = updateStock(details);
            if (!isUpdate) {
                return false;
            }
        }
        return true;
    }

    public Boolean updateStock(OrderDetails orderDetails) throws SQLException {
        String sql = "UPDATE products SET qty = qty - ? WHERE id =?";
        return CRUDutil.execute(sql, orderDetails.getQty(), orderDetails.getItem_code());
    }

}
