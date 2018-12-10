public class Account
{
    String username, emailAdress, password;
    public Account(String emailAdress, String password)
    {
        this.emailAdress = emailAdress;
        this.password = password;
    }

    public String getEmailAdress() {
        return emailAdress;
    }

    public String getPassword() {
        return password;
    }

}
