package repository;

import java.sql.SQLException;
import java.util.List;

public interface CRUDRepository <T,ID>{
    boolean add(T entity) throws SQLException;
    boolean update(T entity);
    boolean deleteById(ID id);
    T searchById(ID id);
    List<T> getAll() throws SQLException;
}
