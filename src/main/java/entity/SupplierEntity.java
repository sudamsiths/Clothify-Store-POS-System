package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "suppliers")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "products")
public class SupplierEntity {
    @Id
    @Column(name = "supplier_id")
    private String supplierId;
    
    @Column(name = "supplier_name", length = 100)
    private String supplierName;
    
    @Column(name = "company_name", length = 100)
    private String companyName;
    
    @Column(name = "email", length = 100, unique = true)
    private String email;
    
    @Column(name = "item")
    private String item;
    
    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductEntity> products;
}