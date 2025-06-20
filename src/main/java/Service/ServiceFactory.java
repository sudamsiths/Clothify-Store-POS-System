package Service;

import Service.custom.impl.EmployeeServiceimpl;
import Service.custom.impl.OrderServiceImpl;
import Service.custom.impl.ProductServiceimpl;
import Service.custom.impl.SupplierServiceimpl;
import util.ServiceType;

public class ServiceFactory {

    private static ServiceFactory instance;

    private ServiceFactory(){}

    public static ServiceFactory getInstance() {
        if (instance == null) {
            instance = new ServiceFactory();
        }
        return instance;
    }

    public <T extends SuperService> T getServiceType(ServiceType type){
        switch (type){
            case Employee:
                return (T) new EmployeeServiceimpl();
            case employeeuser:
                return (T) new EmployeeServiceimpl();
            case Product:
                return (T)new ProductServiceimpl();
            case Supplier:
                return (T)new SupplierServiceimpl();
            case Order:
                return (T)new OrderServiceImpl();
            default:
                return null;
        }
    }
}