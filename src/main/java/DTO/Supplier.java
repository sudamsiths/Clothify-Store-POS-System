package DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Supplier {
    private String supplier_id;
    private String supplier_name;
    private String company_name;
    private String email;
    private String item;

    public Supplier(String supplierId, String supplierName, String companyName, String email, String item, Object o) {
    }
}
