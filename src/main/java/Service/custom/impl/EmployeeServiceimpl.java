package Service.custom.impl;

import DTO.Employee;
import DTO.employeeuser;
import Entity.EmployeeEntity;
import Service.custom.EmployeeService;
import org.modelmapper.ModelMapper;
import repository.DAOFactory;
import repository.custom.EmployeeRepository;
import util.CRUDutil;
import util.RepositoryType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeServiceimpl implements EmployeeService {

    EmployeeRepository employeeRepository = DAOFactory.getInstance().getRepositoryType(RepositoryType.Employee);

    @Override
    public Boolean addEmployee(Employee employee) throws SQLException {
        EmployeeEntity entity = new ModelMapper().map(employee, EmployeeEntity.class);
        return employeeRepository.add(entity);
    }

    @Override
    public Boolean updateEmployee(employeeuser employee) throws SQLException {
        return null;
    }

    @Override
    public employeeuser searchById(String email) throws SQLException {
        ResultSet resultSet = CRUDutil.execute("select * from user where email=?", email);
        if (resultSet.next()) {
            return new employeeuser(
                    resultSet.getString("email"),
                    resultSet.getString("password"));
        } else {
            return null;
        }
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
        employeeuser employee = new ModelMapper().map(employeeuser, employeeuser.class);
        return employeeRepository.adduser(employee);
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

    @Override
    public List<String> getCustomerIds() throws SQLException {
        List<Employee> customers = getAll();
        ArrayList<String> CustomerList = new ArrayList<>();
        customers.forEach(customer -> {
            CustomerList.add(customer.getId());
        });

        return CustomerList;
    }
}
