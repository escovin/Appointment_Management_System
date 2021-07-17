package utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Method to issue a query to supplied database
 * @author Erik Scovin
 */
public class DBQuery {

    private static PreparedStatement statement;

    /**
     * Create statement object
     */
    public static void setPreparedStatement(Connection conn, String sqlStatement) throws SQLException {
        statement = conn.prepareStatement(sqlStatement);
    }

    /**
     * Return statement object
     */
    public static PreparedStatement getPreparedStatement() {
        return statement;
    }

}
