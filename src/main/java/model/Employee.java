package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class Employee {
    private String id;
    private String name;
    private String nic;
    private String address;
    private String DOB;
    private String contactno;
}
