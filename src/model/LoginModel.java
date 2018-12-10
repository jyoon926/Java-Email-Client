package model;

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
        System.out.println(connection);
        if(this.connection == null) {
            System.exit(1);
        }
    }

    public boolean isConnected() {
        return this.connection != null;
    }

    public boolean isLogin(String emailAddress, String password)throws Exception {

        PreparedStatement pr;
        ResultSet rs;

        String sql = "SELECT * FROM login where emailAddress = ? and password = ?";

        try {

            pr = this.connection.prepareStatement(sql);
            pr.setString(1, emailAddress);
            pr.setString(2, password);

            rs = pr.executeQuery();

            boolean boll1;

            System.out.println(pr + "\n" + rs);
            if (pr == null && rs == null) {
                System.out.println("hlkjasfd");
            }

            if (rs.next()) {
                return true;
            }
            return false;
        }
        catch (SQLException e) {
            return false;
        }
    }
}
