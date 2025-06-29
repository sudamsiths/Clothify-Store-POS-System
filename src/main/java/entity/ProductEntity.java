package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import jakarta.persistence.*;

@Entity
@Table(name = "products")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductEntity {
    @Id
    @Column(name = "id", length = 50)
    private String id;
    
    @Column(name = "supplier_id", length = 50)
    private String supplierId;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "category", length = 100)
    private String category;
    
    @Column(name = "size", length = 50)
    private String size;
    
    @Column(name = "price", precision = 10, scale = 2)
    private Double price;
    
    @Column(name = "image_url", length = 500)
    private String imageUrl;
    
    @Column(name = "qty")
    private Integer qty;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", insertable = false, updatable = false)
    private SupplierEntity supplier;
}