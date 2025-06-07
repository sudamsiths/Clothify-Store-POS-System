package Service;

import Service.custom.impl.EmployeeServiceimpl;
import Service.custom.impl.ProductServiceimpl;
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

            default:
                return null;
        }
    }
}