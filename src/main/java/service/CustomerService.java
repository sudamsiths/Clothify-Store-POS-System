package service;

import DTO.Employee;
import entity.CustomerEntity;
import repository.CustomerRepository;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CustomerService {
    
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    
    public CustomerService() {
        this.customerRepository = new CustomerRepository();
        this.modelMapper = new ModelMapper();
    }
    
    public boolean addCustomer(Employee employee) {
        try {
            CustomerEntity entity = modelMapper.map(employee, CustomerEntity.class);
            customerRepository.save(entity);
            return true;
        } catch (Exception e) {
            System.err.println("Error adding customer: " + e.getMessage());
            return false;
        }
    }
    
    public boolean updateCustomer(Employee employee) {
        try {
            CustomerEntity entity = modelMapper.map(employee, CustomerEntity.class);
            customerRepository.save(entity);
            return true;
        } catch (Exception e) {
            System.err.println("Error updating customer: " + e.getMessage());
            return false;
        }
    }
    
    public Employee findCustomerById(String id) {
        Optional<CustomerEntity> entity = customerRepository.findById(id);
        return entity.map(e -> modelMapper.map(e, Employee.class)).orElse(null);
    }
    
    public Employee findCustomerByNic(String nic) {
        Optional<CustomerEntity> entity = customerRepository.findByNic(nic);
        return entity.map(e -> modelMapper.map(e, Employee.class)).orElse(null);
    }
    
    public List<Employee> getAllCustomers() {
        List<CustomerEntity> entities = customerRepository.findAll();
        return entities.stream()
                .map(entity -> modelMapper.map(entity, Employee.class))
                .collect(Collectors.toList());
    }
    
    public List<Employee> searchCustomersByName(String name) {
        List<CustomerEntity> entities = customerRepository.findByNameContaining(name);
        return entities.stream()
                .map(entity -> modelMapper.map(entity, Employee.class))
                .collect(Collectors.toList());
    }
    
    public boolean deleteCustomer(String id) {
        try {
            customerRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            System.err.println("Error deleting customer: " + e.getMessage());
            return false;
        }
    }
    
    public List<String> getAllCustomerIds() {
        return customerRepository.getAllCustomerIds();
    }
    
    public long getCustomerCount() {
        return customerRepository.count();
    }
}