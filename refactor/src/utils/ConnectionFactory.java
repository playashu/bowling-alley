package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *  Singleton class to access DB
 */
public class ConnectionFactory {
    private ConnectionFactory() {}
    private static Connection connection;
    private static String URL="jdbc:sqlite:alley.sqlite";
    public static Connection getConnection(){
        if(ConnectionFactory.connection == null)
        {
            try {
                connection= DriverManager.getConnection(URL);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}
