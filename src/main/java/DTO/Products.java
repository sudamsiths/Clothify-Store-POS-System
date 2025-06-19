package DTO;

import lombok.*;

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

    public Products(String id, String name, String category, String size, Double price, Integer qty) {
    }
}
