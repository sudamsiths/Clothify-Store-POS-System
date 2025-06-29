package service;

import DTO.Products;
import entity.ProductEntity;
import repository.ProductRepository;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProductService {
    
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;
    
    public ProductService() {
        this.productRepository = new ProductRepository();
        this.modelMapper = new ModelMapper();
    }
    
    public boolean addProduct(Products product) {
        try {
            ProductEntity entity = modelMapper.map(product, ProductEntity.class);
            entity.setSupplierId(product.getSupplier_id());
            entity.setImageUrl(product.getImage_url());
            productRepository.save(entity);
            return true;
        } catch (Exception e) {
            System.err.println("Error adding product: " + e.getMessage());
            return false;
        }
    }
    
    public boolean updateProduct(Products product) {
        try {
            ProductEntity entity = modelMapper.map(product, ProductEntity.class);
            entity.setSupplierId(product.getSupplier_id());
            entity.setImageUrl(product.getImage_url());
            productRepository.save(entity);
            return true;
        } catch (Exception e) {
            System.err.println("Error updating product: " + e.getMessage());
            return false;
        }
    }
    
    public Products findProductById(String id) {
        Optional<ProductEntity> entity = productRepository.findById(id);
        return entity.map(this::mapToDTO).orElse(null);
    }
    
    public List<Products> getAllProducts() {
        List<ProductEntity> entities = productRepository.findAll();
        return entities.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    public List<Products> findProductsByCategory(String category) {
        List<ProductEntity> entities = productRepository.findByCategory(category);
        return entities.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    public List<Products> searchProductsByName(String name) {
        List<ProductEntity> entities = productRepository.findByNameContaining(name);
        return entities.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    public List<Products> findProductsBySupplierId(String supplierId) {
        List<ProductEntity> entities = productRepository.findBySupplierId(supplierId);
        return entities.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    public boolean updateStock(String productId, int quantity) {
        return productRepository.updateStock(productId, quantity);
    }
    
    public boolean deleteProduct(String id) {
        try {
            productRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            System.err.println("Error deleting product: " + e.getMessage());
            return false;
        }
    }
    
    public List<String> getAllProductIds() {
        return productRepository.getAllProductIds();
    }
    
    public long getProductCount() {
        return productRepository.count();
    }
    
    private Products mapToDTO(ProductEntity entity) {
        Products dto = modelMapper.map(entity, Products.class);
        dto.setSupplier_id(entity.getSupplierId());
        dto.setImage_url(entity.getImageUrl());
        return dto;
    }
}