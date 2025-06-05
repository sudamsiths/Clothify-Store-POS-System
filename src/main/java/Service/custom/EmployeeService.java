package Service.custom;

import Service.SuperService;
import DTO.Employee;
import DTO.employeeuser;
import Service.custom.impl.EmployeeServiceimpl;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeService extends SuperService {
    Boolean addEmployee(Employee employee) throws SQLException;
    Boolean updateEmployee(Employee employee);
    employeeuser searchById(String email) throws SQLException;
    List<Employee> getAll() throws SQLException;
    List<Employee>deletebyid(String id) throws SQLException;
    Boolean createAccount(employeeuser employeeuser) throws SQLException;
    Employee search(String id) throws SQLException;

}
