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
        String sql = "UPDATE products SET supplier_id=?, name=?, category=?, size=?, price=?, qty=? WHERE id=?";

        Boolean result = CRUDutil.execute(sql,
                products.getSupplier_id(),
                products.getName(),
                products.getCategory(),
                products.getSize(),
                products.getPrice(),
                products.getQty(),
                products.getId()
        );

        return result;
    }
    @Override
    public Products searchProduct(String search) throws SQLException {
        ResultSet resultSet = CRUDutil.execute("SELECT * FROM products WHERE id = ?", search);
        if (resultSet.next()) {
            return new Products(
                    resultSet.getString("id"),
                    resultSet.getString("supplier_id"),
                    resultSet.getString("name"),
                    resultSet.getString("category"),
                    resultSet.getString("size"),
                    resultSet.getDouble("price"),
                    resultSet.getString("image_url"),
                    resultSet.getInt("qty")
            );
        }
        return null;
    }

    @Override
    public List<Products> getAllProduct() throws SQLException {
        List<Products> productsList = new ArrayList<>();

        try (ResultSet resultSet = CRUDutil.execute("select * from products")) {
            while (resultSet.next()) {
               Products products = new Products();
                products.setId(resultSet.getString("id"));
                products.setSupplier_id(resultSet.getString("supplier_id"));
                products.setName(resultSet.getString("name"));
                products.setCategory(resultSet.getString("category"));
                products.setPrice(resultSet.getDouble("price"));
                products.setQty(resultSet.getInt("qty"));
                products.setSize(resultSet.getString("size"));

                productsList.add(products);
            }
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
