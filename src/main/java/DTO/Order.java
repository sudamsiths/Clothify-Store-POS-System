package DTO;
import lombok.*;

import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Order {
    private String id;
    private String date;
    private String customerId;
    private List<OrderDetails> orderDetails;
}
