package javafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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

    private String name;
    private String username;
    private String password;

    public void returnToStart(ActionEvent event) throws Exception
    {
        Parent startParent = FXMLLoader.load(getClass().getResource("Start.fxml"));
        Scene start = new Scene(startParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(start);
        window.show();
    }

    public void startLoginAction(ActionEvent event) throws Exception
    {
        Parent loginParent = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene login = new Scene(loginParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(login);
        window.show();
    }

    public void startCreateAccountAction(ActionEvent event) throws Exception
    {
        Parent createAccountParent = FXMLLoader.load(getClass().getResource("CreateAccount.fxml"));
        Scene createAccount = new Scene(createAccountParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(createAccount);
        window.show();
    }

    public void loginAction(ActionEvent event) throws Exception
    {
        String email = textEmail.getText().toString();
        String password = textPassword.getText().toString();

        if (!(checkUserString(email) && checkPasswordString(password))) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Failed");
            alert.setHeaderText("Your inputs are not valid.");
            alert.showAndWait();
        }
        else if (email.contains("@") && !email.substring(email.indexOf("@") + 1).equals("deppemail.com"))
        {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Failed");
            alert.setHeaderText("Please use '@deppemail' as the domain.");
            alert.showAndWait();
        }
        else
        {
            if (DBAccess.authenticateLogin(email, password)) {
                login(event);
                //name = DBAccess.getName();
                username = email;
                this.password = password;
            } else {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Failed");
                alert.setHeaderText("Please enter a correct email and/or password");
                alert.showAndWait();
            }
        }
    }

    public void login(ActionEvent event) throws IOException
    {
            Parent applicationParent = FXMLLoader.load(getClass().getResource("Application.fxml"));
            Scene application = new Scene(applicationParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(application);
            window.show();
            window.setResizable(false);
    }

    public void createAccountAction(ActionEvent event) throws Exception
    {
        String name = newName.getText().toString();
        String username = newEmail.getText().toString();
        String password = newPassword.getText().toString();
        String passwordRetype = confirmPassword.getText().toString();
        if (username.contains("@"))
        {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Failed");
            alert.setHeaderText("Please enter a username, not an email.");
            alert.showAndWait();
        } 
        else if (!(checkNameString(name) && checkUserString(username) && checkPasswordString(password))) 
        {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Failed");
            alert.setHeaderText("Your inputs are not valid. Try again.");
            alert.showAndWait();
        }
        else if (name.length() > 0 && username.length() > 0 && password.length() > 0 && password.equals(passwordRetype))
        {
            String addAccountOutput = DBAccess.addAccount(name, username, password);
            
            if (addAccountOutput.equals("user already exists"))
            {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Failed");
                alert.setHeaderText("Username is taken.");
                alert.showAndWait();
            }
            else if (addAccountOutput.equals("success"))
            {
                this.name = name;
                this.username = username;
                this.password = password;
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("Welcome to DeppeMail, " + name + ".");
                alert.showAndWait();
                Parent applicationParent = FXMLLoader.load(getClass().getResource("Application.fxml"));
                Scene application = new Scene(applicationParent);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(application);
                window.show();
                window.setResizable(false);
            }
            else
            {
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
    {
    }
    
    public static boolean checkUserString(String str) {
        return str.matches("^\\w+([-+.']\\w+)*$");
    }

    public static boolean checkPasswordString(String str) {
        //general check to prevent unwanted characters
        boolean generalCheck = str.matches("^\\w+([-+.'\\[\\]]\\w*)*$");
        //check for unsavory things in brackets or dot after brackets
        boolean bracketCheck = str.matches(	"[\\{\\[\\(][\\d\"']+[\\}\\]\\)]*|[\\)\\]]\\.");
        
        return generalCheck && bracketCheck;
    }  
    
    public static boolean checkNameString(String str) {
        return !str.matches("^[\\p{L}\\s'.-]+$");
    }
}
