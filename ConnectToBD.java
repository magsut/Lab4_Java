package Lab4.Utils;

import java.sql.*;

public class ConnectToBD {
    public static Connection getConnection() throws SQLException
    {
        return DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/lab4_java?characterEncoding=UTF8",
                "root",
                "1234"
        );
    }
}