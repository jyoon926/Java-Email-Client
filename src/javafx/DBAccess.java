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
            String myUrl = "jdbc:mysql://10.180.1.207";
            Class.forName(myDriver);
            conn = DriverManager.getConnection(myUrl,
                "dbuser", "password"); //im good at security I swear :P

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static String addAccount(String name_, String user_, String password_)
    {
        try
        {
            //create a statement
            PreparedStatement st = conn.prepareStatement(
                "SELECT * FROM users.users WHERE user = ?"
            );
            st.setString(1, user_);

            //execute query, and get a resultset
            ResultSet rs = st.executeQuery();

            //check if user exists
            if (rs.next())
            {
                return "user already exists";
            }
            st.close();

            st = conn.prepareStatement(
                "INSERT INTO users.users (name, user, password) VALUES (?, ?, ?)"
            );
            st.setString(1, name_);
            st.setString(2, user_);
            st.setString(3, hashString(password_));

            //create user;
            st.executeUpdate();

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

            //create a statement
            PreparedStatement st = conn.prepareStatement(
                "SELECT * FROM users.users WHERE user = ?"
            );
            st.setString(1, user_);

            //execute query, and get a resultset
            ResultSet rs = st.executeQuery();

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

            if (authenticateLogin(user_, password_))
            {
                //create a statement
                PreparedStatement st = conn.prepareStatement(
                    "DELETE FROM users.users WHERE user = ? AND password = ?"
                );
                st.setString(1, user_);
                st.setString(2, hashString(password_));

                //delete user
                st.executeUpdate();

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

        try
        {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM users.users WHERE user = ? AND password = ?");
            st.setString(1, user);
            st.setString(2, hashString(password));
            
            ResultSet rs = st.executeQuery();
            
            String uid = null;
            
            while(rs.next()) {
                uid = rs.getString("userid");
            }
            st.close();
            
            return uid;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "error";
        }
    }

    public static String getName(String user, String password) 
    {
        try
        {
            PreparedStatement st = conn.prepareStatement("SELECT * FROM users.users WHERE user = ? AND password = ?");
            st.setString(1, user);
            st.setString(2, hashString(password));
            
            ResultSet rs = st.executeQuery();
            
            String name = null;
            
            while(rs.next()) {
                name = rs.getString("name");
            }
            st.close();
            
            return name;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return "error";
        }
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