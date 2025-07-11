package util;

import db.DBConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CRUDutil {

    public static <T> T execute(String sql, Object... args) throws SQLException {
        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement(sql);

        for (int i = 0; i < args.length; i++) {
            preparedStatement.setObject((i + 1), args[i]);
        }
        if (sql.startsWith("SELECT") || sql.startsWith("select")) {
            return (T) preparedStatement.executeQuery();
        }
        return (T) (Boolean) (preparedStatement.executeUpdate() > 0);
    }
}
