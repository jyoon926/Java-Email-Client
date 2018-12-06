package dbUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbConnection {
    private static final String USERNAME = "dbuser";
    private static final String PASSWORD = "dbpassword";
    private static final String CONN = "jdbc:mysql://localhost/Main";
    private static final String SQCONN = "jdbc:sqlite:Accounts.db";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.sqlite.JBDC");
            return DriverManager.getConnection(SQCONN);
        }
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
