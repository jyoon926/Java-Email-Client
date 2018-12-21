package javafx;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;

/**
 * Allows communication with database for messages exchange
 * @author Michael Kassabov
 * @version December 20th, 2018
 */
public class MailSendReceive
{
	
	/**
	 * sends a messgae to be stored on a serrver and retreived later.
	 * @param sender the eser that sends the mssage
	 * @param recipient the user meant to receive the message
	 * @param msg the message being sent
	 * @return boolean value of success.
	 */
    public static Boolean sendMessage(String sender, String recipient, String msg)
    {
        try
        {
        	PreparedStatement st = DBAccess.conn.prepareStatement(
                "INSERT INTO deppemail.emaul (sender, recipient, msg) VALUES (?, ?, ?)"
            );
            st.setString(1, sender);
            st.setString(2, recipient);
            st.setString(3, msg);

            //create msg
            st.executeUpdate();

            st.close();

            return true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * retreives messages of a given user
     * @param user user to retreive messages for
     * @return arraylist of messages
     */
    public static ArrayList<String> receiveMessage(String user)
    {

        try
        {
        	//query database for UID
            PreparedStatement st = DBAccess.conn.prepareStatement(
                "SELECT * FROM deppemail.emaul WHERE recipient = ?"
            );
            st.setString(1, user);;

            ResultSet rs = st.executeQuery();

            ArrayList<String> emails = new ArrayList<String>();
            
            while (rs.next())
            {
                emails.add(rs.getString("sender") + ": " + rs.getString("msg"));
            }
            
            st.close();

            return emails;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return new ArrayList<String>();
        }
    }
}