package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "orderDetails")
public class OrderEntity {
    @Id
    @Column(name = "id", length = 50)
    private String id;
    
    @Column(name = "order_date")
    private LocalDate orderDate;
    
    @Column(name = "customer_id", length = 50)
    private String customerId;
    
    @Column(name = "total_amount", precision = 10, scale = 2)
    private Double totalAmount;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", insertable = false, updatable = false)
    private CustomerEntity customer;
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderDetailEntity> orderDetails;
}