package Service;

import Service.custom.impl.EmployeeServiceimpl;
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
            default:
                return null;
        }
    }
}