package DTO;

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
    private String category;
    private String size;
    private Double unitPrice;
    private Double total;

    public CartTM(String itemcode, String itemname, Integer qtyonhand, Double unitprice, String category, String size, Double total) {
    }
}
