package Service.custom.impl;

import Service.custom.EmployeeService;
import DTO.Employee;
import DTO.employeeuser;
import util.CRUDutil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeServiceimpl implements EmployeeService {


    @Override
    public Boolean addEmployee(Employee employee) throws SQLException {

        return CRUDutil.execute("INSERT INTO customer VALUES (?,?,?,?,?,?)",
                employee.getId(),
                employee.getName(),
                employee.getNic(),
                employee.getAddress(),
                employee.getDOB(),
                employee.getContactno()
        );
    }

    @Override
    public Boolean updateEmployee(Employee employee) {
        return null;
    }

    @Override
    public employeeuser searchById(String email) throws SQLException {
        ResultSet resultSet = CRUDutil.execute("SELECT * FROM user WHERE email=?", email);
        if (resultSet.next()) {
            return new employeeuser(
                    resultSet.getString("email"),
                    resultSet.getString("password")
            );
        }
        return null;
    }

    @Override
    public List<Employee> getAll() throws SQLException {
        List<Employee> employeeList = new ArrayList<>();

        try (ResultSet resultSet = CRUDutil.execute("select * from customer")) {
            while (resultSet.next()) {
                Employee employee = new Employee();
                employee.setId(resultSet.getString("id"));
                employee.setName(resultSet.getString("name"));
                employee.setNic(resultSet.getString("nic"));
                employee.setAddress(resultSet.getString("address"));
                employee.setDOB(resultSet.getDate("DOB"));
                employee.setContactno(resultSet.getString("contactno"));

                employeeList.add(employee);
            }
        }

        return employeeList;
    }

    @Override
    public List<Employee> deletebyid(String id) throws SQLException {
        return List.of();
    }

    @Override
    public Boolean createAccount(employeeuser employeeuser) throws SQLException {
        return CRUDutil.execute("insert into user values (?,?)",
        employeeuser.getEmail(),employeeuser.getPassword());
    }

    @Override
    public Employee search(String id) throws SQLException {
        ResultSet resultSet = CRUDutil.execute("SELECT * FROM customer WHERE id=?", id);
        if (resultSet.next()) {
            return new Employee(
                    resultSet.getString("id"),
                    resultSet.getString("name"),
                    resultSet.getString("nic"),
                    resultSet.getString("address"),
                    resultSet.getDate("dob"),
                    resultSet.getString("contactno")
            );
        }
        return null;
    }
}
