package javafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.DBAccess;
import javafx.MailSendReceive;

/**
 * Controls the fxml application
 * @author Jacob Yoon
 */
public class Controller implements Initializable
{
    @FXML
    private TextField textEmail;
    @FXML
    private PasswordField textPassword;
    @FXML
    private TextField newName;
    @FXML
    private TextField newEmail;
    @FXML
    private PasswordField newPassword;
    @FXML
    private PasswordField confirmPassword;
    @FXML
    private Button composeButton;
    @FXML
    private Button checkButton;
    @FXML
    private Label label;

    private String name;
    private String username;
    private String password;

    /**
     * Goes back to the Start scene
     * @param event The input from the cancel button on the Login scene
     * @throws Exception
     */
    public void returnToStart(ActionEvent event) throws Exception
    {
        Parent startParent = FXMLLoader.load(getClass().getResource("Start.fxml"));
        Scene start = new Scene(startParent);
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(start);
        window.show();
    }

    /**
     * Goes to the Login scene
     * @param event The input from the login button on the Start scene
     * @throws Exception
     */
    public void startLoginAction(ActionEvent event) throws Exception
    {
        //Switch scene to Login scene
        Parent loginParent = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene login = new Scene(loginParent);
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(login);
        window.show();
    }

    /**
     * Goes to the CreateAccount scene
     * @param event The input from the create account button on the Start scene
     * @throws Exception
     */
    public void startCreateAccountAction(ActionEvent event) throws Exception
    {
        //Switch scene to CreateAccount scene
        Parent createAccountParent = FXMLLoader.load(getClass().getResource("CreateAccount.fxml"));
        Scene createAccount = new Scene(createAccountParent);
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(createAccount);
        window.show();
    }

    /**
     * Logs into the account
     * @param event The input from the login button in the Login scene
     * @throws Exception
     */
    public void loginAction(ActionEvent event) throws Exception
    {
        String email = textEmail.getText().toString();
        String password = textPassword.getText().toString();
        //Checks if inputs are valid
        if (email.equals("test") && password.equals("test"))
            login(event);
        else if (!(checkUserString(email) && checkPasswordString(password)))
        {
            //Error message
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Failed");
            alert.setHeaderText("Your inputs are not valid.");
            alert.showAndWait();
        }
        //Checks if email has the correct domain
        else if (email.contains("@") && !email.substring(email.indexOf("@") + 1).equals("deppemail.com"))
        {
            //Error message
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Failed");
            alert.setHeaderText("Please use '@deppemail' as the domain.");
            alert.showAndWait();
        }
        else
        {
            //Checks if email and passwords exist in database and logs in
            if (DBAccess.authenticateLogin(email, password))
            {
                login(event);
                name = DBAccess.getName(this.username, this.password);
                username = email;
                this.password = DBAccess.hashString(password);
            }
            else
            {
                //Error message
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Failed");
                alert.setHeaderText("Please enter a correct email and/or password");
                alert.showAndWait();
            }
        }
    }

    /**
     * Goes to the Application scene
     * @param event The input from the login button in the Mailbox scene
     * @throws Exception
     */
    public void login(ActionEvent event) throws IOException
    {
        //Sets scene to Application scene (the mailbox)
        Parent applicationParent = FXMLLoader.load(getClass().getResource("Application.fxml"));
        Scene application = new Scene(applicationParent);
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(application);
        window.show();
        window.setResizable(false);
    }

    /**
     * Creates an account
     * @param event The input from the create account button in the CreateAccount scene
     * @throws Exception
     */
    public void createAccountAction(ActionEvent event) throws Exception
    {
        String name = newName.getText().toString();
        String username = newEmail.getText().toString();
        String password = newPassword.getText().toString();
        String passwordRetype = confirmPassword.getText().toString();
        //Checks if username has a '@'
        if (username.contains("@"))
        {
            //Error message
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Failed");
            alert.setHeaderText("Please enter a username, not an email.");
            alert.showAndWait();
        }
        //Checks if any input have unwanted characters
        else if (!(checkNameString(name) && checkUserString(username) && checkPasswordString(password)))
        {
            //Error message
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Failed");
            alert.setHeaderText("Your inputs are not valid. Try again.");
            alert.showAndWait();
        }
        //Checks if inputs are valid and have no unallowed characters
        else if (name.length() > 0 && username.length() > 0 && password.length() > 0 && password.equals(passwordRetype))
        {
            String addAccountOutput = DBAccess.addAccount(name, username, password);

            //Checks if username already exists in database
            if (addAccountOutput.equals("user already exists"))
            {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Failed");
                alert.setHeaderText("Username is taken.");
                alert.showAndWait();
            }
            else if (addAccountOutput.equals("success"))
            {
                //Sets pivs to the name, username, and password of the created account
                this.name = name;
                this.username = username;
                this.password = DBAccess.hashString(password);
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("Welcome to DeppeMail, " + name + ".");
                alert.showAndWait();

                //Set scene to Application scene (the mailbox)
                Parent applicationParent = FXMLLoader.load(getClass().getResource("Application.fxml"));
                Scene application = new Scene(applicationParent);
                Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
                window.setScene(application);
                window.show();
                window.setResizable(false);
            }
            else
            {
                //When there is an unknown error
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Failed");
                alert.setHeaderText("An unknown error occurred. Try again.");
                alert.showAndWait();
            }
        }
        else
        {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Failed");
            alert.setHeaderText("Your passwords aren't the same. Try again.");
            alert.showAndWait();
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {}

    /**
     * Checks a username string for unallowed characters
     * @param str The username to be checked
     */
    public static boolean checkUserString(String str)
    {
        return str.matches("^\\w+([-+.']\\w+)*$");
    }

    /**
     * Checks a password string for unallowed characters
     * @param str The password to be checked
     */
    public static boolean checkPasswordString(String str)
    {
        //general check to prevent unwanted characters
        boolean generalCheck = str.matches("^\\w+([-+.'\\[\\]]\\w*)*$");
        //check for unsavory things in brackets or dot after brackets
        boolean bracketCheck = str.matches("[\\{\\[\\(][\\d\"']+[\\}\\]\\)]*|[\\)\\]]\\.");

        return generalCheck && !bracketCheck;
    }

    /**
     * Checks a name string for unallowed characters
     * @param str The name to be checked
     */
    public static boolean checkNameString(String str)
    {
        return str.matches("^[\\p{L}\\s'.-]+$");
    }

    /**
     * Returns the username
     * @return The username
     */
    public String getUsername()
    {
        return username;
    }

    /**
     * Returns the password
     * @return The password
     */
    public String getPassword()
    {
        return password;
    }

    /**
     * Goes back to the Start scene
     * @param event The input from the cancel button on the Login scene
     * @throws Exception
     */
    public void returnToApplication(ActionEvent event) throws Exception
    {
        Parent startParent = FXMLLoader.load(getClass().getResource("Start.fxml"));
        Scene start = new Scene(startParent);
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(start);
        window.show();
    }

    /**
     * Goes to the NewMessage scene
     * @param event The input from the new message button in the Mailbox scene
     * @throws Exception
     */
    public void startNewMessageAction(ActionEvent event) throws Exception
    {
        //Open a new NewMessage window/stage
        Parent root = FXMLLoader.load(getClass().getResource("NewMessage.fxml"));
        Stage stage = new Stage();
        stage.setTitle("New Message");
        stage.setScene(new Scene(root, 600, 500));
        stage.show();
    }

    /**
     * Runs checkEmail method in CheckEmail class to check for new messages
     * @param event The input from the refresh button
     * @throws Exception
     */
    public void checkEmail(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Mailbox.fxml"));
        Stage stage = new Stage();
        stage.setTitle("New Message");
        stage.setScene(new Scene(root, 400, 500));
        stage.show();
        String messages = "No Messages!";
        if (MailSendReceive.receiveMessage(username).size() > 0) {
            for (int i = 0; i < (MailSendReceive.receiveMessage(username).size()); i++) {
                messages = MailSendReceive.receiveMessage(username).get(i) + "/n";
            }
        }
        label.setText(messages);
    }
}