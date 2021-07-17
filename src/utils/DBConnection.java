package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Connects to supplied database
 *
 * @author Erik Scovin
 */
public class DBConnection {

    /**
     * JDBC URL parts
     */
    private static final String protocol = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//wgudb.ucertify.com/WJ07wtU";
    //private static final String dbName = "WJ07wtU"; //adding DB name

    /**
     * JDBC URL
     */
    private static final String jdbcURL = protocol + vendorName + ipAddress;

    /**
     * Driver interface reference
     */
    private static final String MYSQLJDBCDriver = "com.mysql.cj.jdbc.Driver";
    private static Connection conn = null;

    private static final String username = "U07wtU"; //Username
    private static final String password = "53689154674"; //Password

    /**
     * Sets up database connection
     */
    public static Connection startConnection()
    {
        try {
            Class.forName(MYSQLJDBCDriver);
            conn = DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connection successful!");
        }
        catch(ClassNotFoundException e)
        {
            e.printStackTrace();
            //System.out.println(e.getMessage());
        }
        catch(SQLException e)
        {
           // System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }

        return conn;
    }

    public static Connection getConnection(){
        return conn;
    }

    public static void closeConnection() {
        try {
            conn.close();
            System.out.println("Connection closed.");
        }
        catch (SQLException e) {
            System.out.print("Error" + e.getMessage());
        }
    }

}
