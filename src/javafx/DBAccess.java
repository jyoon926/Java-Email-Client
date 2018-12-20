package javafx;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;

/**
 * Allows communication with database
 * @author Michael Kassabov
 * @version December 20th, 2018
 */
public class DBAccess
{

    private static Connection conn;

    /**
     * creates a connection to the database.
     * needs to be run first.
     */
    public static void startConnection()
    {
        try
        {
            // create database connection
            String myDriver = "com.mysql.cj.jdbc.Driver";
            //String myUrl = "jdbc:mysql://67.241.76.237";
            String myUrl = "jdbc:mysql://10.180.4.156";
            Class.forName(myDriver);
            conn = DriverManager.getConnection(myUrl,
                "deppemail_user", "tempPass"); //im good at security I swear :P

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new account to the database.
     * @param name_ the name of the new user
     * @param user_ the username of the new user
     * @param password_ the password of the new users
     * @return status of adding account to database
     */
    public static String addAccount(String name_, String user_, String password_)
    {
        try
        {
            //create a statement
            PreparedStatement st = conn.prepareStatement(
                "SELECT * FROM deppemail.users WHERE user = ?"
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
                "INSERT INTO deppemail.users (name, user, password) VALUES (?, ?, ?)"
            );
            st.setString(1, name_);
            st.setString(2, user_);
            st.setString(3, hashString(password_));

            //create user
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

    /**
     * authenticates the login for a user.
     * @param user_ login username
     * @param password_ login password
     * @return boolean of login status
     */
    public static boolean authenticateLogin(String user_, String password_)
    {
        try
        {

            //create a statement
            PreparedStatement st = conn.prepareStatement(
                "SELECT * FROM deppemail.users WHERE user = ?"
            );
            st.setString(1, user_);

            //execute query, and get a resultset
            ResultSet rs = st.executeQuery();

            //retreive password hash
            String password = null;
            while (rs.next())
            {
                password = rs.getString("password");
            }
            st.close();

            //check if password hashes match
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

    /**
     * permanently deletes an account from the database
     * @param user_ username to delete
     * @param password_ password to delete
     * @return status of account deletion
     */
    public static boolean removeAccount(String user_, String password_)
    {

        try
        {
        	//check user authentication first
            if (authenticateLogin(user_, password_))
            {
                //create a statement
                PreparedStatement st = conn.prepareStatement(
                    "DELETE FROM deppemail.users WHERE user = ? AND password = ?"
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

    /**
     * Returns the userID of the user.
     * @param user username
     * @param password password
     * @return the userID of the user
     */
    public static String getUID(String user, String password)
    {

        try
        {
        	//query database for UID
            PreparedStatement st = conn.prepareStatement(
                "SELECT * FROM deppemail.users WHERE user = ? AND password = ?"
            );
            st.setString(1, user);
            st.setString(2, hashString(password));

            ResultSet rs = st.executeQuery();

            //retrieve userID from query ouput
            String uid = null;
            while (rs.next())
            {
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

    /**
     * Returns the name of the user.
     * @param user username
     * @param password password
     * @return the name of the user
     */
    public static String getName(String user, String password)
    {
        try
        {
        	//query database for name
            PreparedStatement st = conn.prepareStatement(
                "SELECT * FROM deppemail.users WHERE user = ? AND password = ?"
            );
            st.setString(1, user);
            st.setString(2, hashString(password));

            ResultSet rs = st.executeQuery();

            //retrieve name from query output
            String name = null;
            while (rs.next())
            {
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

    /**
     * returns a hashed verion of an input string
     * uses MD5 hash
     * @param str string to hash
     * @return hashed string
     */
    public static String hashString(String str)
    {

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