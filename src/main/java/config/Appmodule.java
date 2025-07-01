package config;

import Service.custom.EmployeeService;
import Service.custom.OrderDetailsService;
import Service.custom.OrderService;
import Service.custom.ProductService;
import Service.custom.impl.EmployeeServiceimpl;
import Service.custom.impl.OrderDetailServiceImpl;
import Service.custom.impl.OrderServiceImpl;
import Service.custom.impl.ProductServiceimpl;
import com.google.inject.AbstractModule;
import repository.custom.EmployeeRepository;
import repository.custom.OrderRepository;
import repository.custom.ProductRepository;
import repository.custom.SupplierRepository;
import repository.custom.impl.EmployeeRepositoryimpl;
import repository.custom.impl.OrderRepositoryimpl;
import repository.custom.impl.SupplierRepositoryimpl;

public class Appmodule extends AbstractModule {

    @Override
    protected void configure() {
        bind(EmployeeService.class).to(EmployeeServiceimpl.class);
        bind(ProductService.class).to(ProductServiceimpl.class);
        bind(OrderService.class).to(OrderServiceImpl.class);
         bind(OrderService.class).to(OrderServiceImpl.class);
         bind(EmployeeRepository.class).to(EmployeeRepositoryimpl.class);
//         bind(ProductRepository.class).to(ProductRepository.class);
         bind(OrderRepository.class).to(OrderRepositoryimpl.class);
         bind(SupplierRepository.class).to(SupplierRepositoryimpl.class);
    }
}
