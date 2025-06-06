package Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SupplierEntity {
    private Integer supplier_id;
    private String supplier_name;
    private String company_name;
    private String email;

}
