package javafx;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

public class DBAccess
{

    private static Connection conn;

    //needs to be run first
    public static void startConnection()
    {
        try
        {
            // create database connection
            String myDriver =
                "com.mysql.cj.jdbc.Driver";
            //this URL will change when server is hosted somewhere
            String myUrl = "jdbc:mysql://10.0.8.240";
            Class.forName(myDriver);
            conn = DriverManager.getConnection(myUrl,
                "dbuser", "password");

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static String addAccount(String user_, String password_)
    {
        try
        {
            //SQL SELECT query
            String query =
                "SELECT * FROM users.users WHERE user = '" +
                user_ + "'";

            //SQL INSERT to insert user
            String insert =
                "INSERT INTO users.users (user, password) VALUES ('" +
                user_ + "', '" + hashString(password_) +
                "')";

            //create a statement
            Statement st = conn.createStatement();

            //execute query, and get a resultset
            ResultSet rs = st.executeQuery(query);

            //check if user exists
            if (rs.next())
            {
                return "user already exists";
            }

            //create user;
            st.executeUpdate(insert);

            st.close();

            return "success";
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "an error occured";
        }
    }

    public static boolean authenticateLogin(String user_,
        String password_)
    {
        try
        {
            //SQL SELECT query
            String query =
                "SELECT * FROM users.users WHERE user = '" +
                user_ + "'";

            //create a statement
            Statement st = conn.createStatement();

            //execute query, and get a resultset
            ResultSet rs = st.executeQuery(query);

            String password = null;

            while (rs.next())
            {
                password = rs.getString("password");
            }
            st.close();

            if (password.equals(hashString(password_)))
            {
                return true;
            }

            return false;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean removeAccount(String user_,
        String password_)
    {

        try
        {
            //SQL DELTE to delete user
            String delete =
                "DELETE FROM users.users WHERE user = '" +
                user_ + "' AND password = '" +
                hashString(password_) + "'";

            if (authenticateLogin(user_, password_))
            {
                //create a statement
                Statement st = conn.createStatement();

                //delete user
                st.executeUpdate(delete);

                st.close();

                return true;
            }

            return false;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }

    }

    public static String getUID(String user, String password)
    {

        /*
         * TODO:
         * implement
         */

        return "";
    }

    public static String hashString(String str)
    {

        /*
         * TODO:
         * add salting
         * switch to SHA-256
         */

        String generatedPassword = null;
        try
        {
            MessageDigest md = MessageDigest.getInstance(
                "MD5");
            md.update(str.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++)
            {
                sb.append(Integer.toString((bytes[i] &
                    0xff) + 0x100, 16).substring(
                    1));
            }
            generatedPassword = sb.toString();
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return generatedPassword;
    }

}