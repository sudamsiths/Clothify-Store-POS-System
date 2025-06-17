package DTO;

import lombok.*;

@Setter
@Getter
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

    public Products(String supplierId, String name, String category, String size, double price, int qty) {
    }

    public Products(String title, String category, String size, Double price, String qty) {
    }

    public Products(String id, String supplierId, String title, String category, String size, Double price, Integer qty) {
    }

    public Products(String title, String category, String size, Double price, Integer qty) {
    }
}
