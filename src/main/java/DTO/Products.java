package DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Products {
    private String id;
    private String supplier_id;
    private String name;
    private String category;
    private String size;
    private Double price;
    private Integer qty;
    private String image_url;
}
