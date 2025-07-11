package Entity;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class OrderDetailsEntity {
    private String id;
    private String item_code;
    private String category;
    private String size;
    private Integer qty;
    private Double unit_price;
    private Double total;
}
