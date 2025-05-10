package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static DbConnection DB_CONNECTION;
    private Connection connection;

    private DbConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_dsmp_5","root","1234");
    }
    public static DbConnection getInstance() throws SQLException, ClassNotFoundException {
        if (DB_CONNECTION == null) {
            DB_CONNECTION = new DbConnection();
        }
        return DB_CONNECTION;
    }
    public Connection getConnection() {
        return connection;
    }
}
