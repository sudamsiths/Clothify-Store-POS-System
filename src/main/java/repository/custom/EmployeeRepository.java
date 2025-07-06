package repository.custom;

import DTO.employeeuser;
import Entity.EmployeeEntity;
import repository.CRUDRepository;

public interface EmployeeRepository extends CRUDRepository<EmployeeEntity, String> {

    Boolean adduser(employeeuser employee);
}
