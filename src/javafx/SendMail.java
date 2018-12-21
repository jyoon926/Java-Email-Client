package javafx;

import java.util.*;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail
{
    Properties propvls = new Properties();
    private String recipentAddr;
    private String senderAddr;
    private String username;
    private String password;
    private String smtphost;

    public SendMail(String a, String b, String c, String d, String e)
    {
        recipentAddr = a;
        senderAddr = b;
        username = c;
        password = d;
        smtphost = e;
    }

    public void initializeSmtp(Boolean usesAuth, Boolean usesTls, int port)
    {
        propvls.put("mail.smtp.auth", usesAuth);
        propvls.put("mail.smtp.starttls.enable", usesTls);
        propvls.put("mail.smtp.host", this.smtphost);
        propvls.put("mail.smtp.port", port);
    }

    public void emailThatMessage(String message, String subject)
    {
        Session sessionobj = Session.getInstance(propvls,
                new javax.mail.Authenticator()
                {
                    protected PasswordAuthentication getPasswordAuthentication()
                    {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try
        {
            //Create MimeMessage object & set values
            Message messageobj = new MimeMessage(sessionobj);
            messageobj.setFrom(new InternetAddress(this.senderAddr));
            messageobj.setRecipients(Message.RecipientType.TO,InternetAddress.parse(this.recipentAddr));
            messageobj.setSubject(subject);
            messageobj.setText(message);
            //Now send the message
            Transport.send(messageobj);
            System.out.println("Your email sent successfully....");
        }
        catch (MessagingException exp)
        {
            throw new RuntimeException(exp);
        }
    }
}