package Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartTM {
    private String itemCode;
    private String description;
    private Integer qtyOnHand;
    private Double unitPrice;
    private String category;
    private String size;
    private Double total;
}