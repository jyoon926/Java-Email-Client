import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dbUtil.dbConnection;

public class LoginModel {
    Connection connection;

    public LoginModel() {
        try {
            this.connection = dbConnection.getConnection();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        if(this.connection == null) {
            System.exit(1);
        }
    }

    public boolean isConnected() {
        return this.connection != null;
    }

    public boolean isLogin(String address, String pass, String opt)throws Exception {

        PreparedStatement pr = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM login where emailAddress = ? and password = ?";

        try {

            pr = this.connection.prepareStatement(sql);
            pr.setString(1, address);
            pr.setString(2, pass);

            rs = pr.executeQuery();

            boolean boll1;

            if (rs.next()) {
                return true;
            }
            return false;
        }
        catch (SQLException e) {
            return false;
        }
        finally {
            pr.close();
            rs.close();
        }
    }
}
