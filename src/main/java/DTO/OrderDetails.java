package DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class    OrderDetails {
    private String id;
    private String item_code;
    private String category;
    private String size;
    private Integer qty;
    private Double unit_price;

    public OrderDetails(String orderId, String itemCode, Integer qtyOnHand, Double unitPrice) {
    }

    public OrderDetails(String itemCode, String category, String size, Integer qtyOnHand, Double unitPrice) {
    }
}
