package Entity;

import DTO.OrderDetails;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class OrderEntity {
    private String id;
    private String date;
    private String customerId;
    private List<OrderDetails> orderDetails;
}
