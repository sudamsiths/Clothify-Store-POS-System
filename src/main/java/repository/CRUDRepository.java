package repository;

import java.sql.SQLException;
import java.util.List;

public interface CRUDRepository<T, ID> extends SuperRepository {
    boolean add(T entity) throws SQLException;

    boolean update(T entity) throws SQLException;

    boolean deleteById(ID id);

    T searchById(ID id) throws SQLException;

    List<T> getAll() throws SQLException;
}
