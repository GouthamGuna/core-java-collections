package in.dev.gmsk.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConnection {

    private final String URL;
    private final String userName;
    private final String password;

    public JDBCConnection(String URL, String userName, String password) {
        this.URL = URL;
        this.userName = userName;
        this.password = password;
    }

    public static Connection getConnection(JDBCConnection connection) {
        try {
            return DriverManager.getConnection(connection.URL, connection.userName, connection.password);
        } catch (SQLException e) {
            throw new RuntimeException(STR."Error : \{e}");
        }
    }
}
