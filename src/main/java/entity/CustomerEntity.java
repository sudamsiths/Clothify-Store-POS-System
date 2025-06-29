package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "customer")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CustomerEntity {
    @Id
    @Column(name = "id", length = 50)
    private String id;
    
    @Column(name = "name", length = 100)
    private String name;
    
    @Column(name = "nic", unique = true)
    private String nic;
    
    @Column(name = "address")
    private String address;
    
    @Column(name = "dob")
    private LocalDate dob;
    
    @Column(name = "contactno")
    private String contactno;
}