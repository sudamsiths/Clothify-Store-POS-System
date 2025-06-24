package DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private String id;
    private Date date;
    private String customer_id;
    private List<OrderDetails> orderDetails;

    public Order(String orderId, String date, String customerId, List<OrderDetails> orderDetails) {
    }

    public Order(String date, String customerId, List<OrderDetails> orderDetails) {
    }

}

