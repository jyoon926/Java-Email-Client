public class Account
{
    String username, emailAdress, password;
    public Account(String username, String emailAdress, String password)
    {
        this.emailAdress = emailAdress;
        this.password = password;
        this.username = username;
    }

    public String getEmailAdress() {
        return emailAdress;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
