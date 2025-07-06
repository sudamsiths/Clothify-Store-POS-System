package repository;

import repository.custom.impl.EmployeeRepositoryimpl;
import repository.custom.impl.OrderRepositoryimpl;
import repository.custom.impl.ProductRepositoryimpl;
import repository.custom.impl.SupplierRepositoryimpl;
import util.RepositoryType;

public class DAOFactory {
    private static DAOFactory instance;

    private DAOFactory() {
    }

    public static DAOFactory getInstance() {
        return instance == null ? instance = new DAOFactory() : instance;
    }

    public <T extends SuperRepository> T getRepositoryType(RepositoryType type) {
        switch (type) {
            case Employee:
                return (T) new EmployeeRepositoryimpl();
            case employeeuser:
                return (T) new EmployeeRepositoryimpl();
            case Product:
                return (T) new ProductRepositoryimpl();
            case Supplier:
                return (T) new SupplierRepositoryimpl();
            case Order:
                return (T) new OrderRepositoryimpl();
            default:
                return null;
        }
    }
}
