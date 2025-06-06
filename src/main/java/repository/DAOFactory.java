package repository;

import repository.custom.EmployeeRepository;
import repository.custom.impl.EmployeeRepositoryimpl;
import util.RepositoryType;

public class DAOFactory {
    private static DAOFactory instance;

    private DAOFactory(){}

    public static DAOFactory getInstance() {
        return instance == null ?instance = new DAOFactory():instance;
    }

    public <T extends SuperRepository> T getRepositoryType(RepositoryType type){
        switch (type){
            case Employee:
                return (T) new EmployeeRepositoryimpl();
            case employeeuser:
                return (T) new EmployeeRepositoryimpl();
            default:
                return null;
        }
    }
}
