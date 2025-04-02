package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static String URL = "jdbc:mysql://localhost:3306/shop";
    public static String USER = "root";
    public static String PASSWORD = "";
    public static Connection connection = null;

    public static Connection getConnection(){
        try {
            if(connection == null || connection.isClosed()){
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        }
        catch(ClassNotFoundException | SQLException exception){
            exception.getStackTrace();
            throw new RuntimeException("Error while connecting to DB");
        }
        return connection;
    }

    public static void closeConnection() throws SQLException {
        connection.close();
    }
}
