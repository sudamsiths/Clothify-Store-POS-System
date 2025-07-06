package DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ManyToAny;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetails {
    private String id;
    @ManyToAny
    private String item_code;
    private String category;
    private String size;
    private Integer qty;
    private Double unit_price;
    private Double total;

    public OrderDetails(String orderId, String itemCode, Integer qtyOnHand, Double unitPrice) {
    }

    public OrderDetails(String itemCode, String category, String size, Integer qtyOnHand, Double unitPrice) {
    }

    public OrderDetails(String orderId, String itemCode, String category, String size, Integer qtyOnHand, Double unitPrice, Object o, Double total) {
    }
}
