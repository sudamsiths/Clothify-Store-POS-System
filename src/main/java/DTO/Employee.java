package DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Employee extends employeeuser {
    private String id;
    private String name;
    private String nic;
    private String address;
    private Date DOB;
    private String contactno;


    public Employee(String id, String name, String address, double salary) {
    }

    public Employee(String email, String password) {
    }
}
