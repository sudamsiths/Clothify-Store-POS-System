package Service.custom;

import DTO.Employee;
import DTO.employeeuser;
import Service.SuperService;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeService extends SuperService {
    Boolean addEmployee(Employee employee) throws SQLException;

    Boolean updateEmployee(employeeuser employee) throws SQLException;

    employeeuser searchById(String email) throws SQLException;

    List<Employee> getAll() throws SQLException;

    List<Employee> deletebyid(String id) throws SQLException;

    Boolean createAccount(employeeuser employeeuser) throws SQLException;

    Employee search(String id) throws SQLException;

    List<String> getCustomerIds() throws SQLException;
}
