package DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private String id;
    private String date;
    private String customer_id;
    private List<OrderDetails> orderDetails;
}

