package DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetails {
    private String id;
    private String itemCode;
    private String category;
    private String size;
    private Integer qty;
    private Double unitPrice;

    public OrderDetails(String orderId, String itemCode, Integer qtyOnHand, Double unitPrice) {
    }
}
