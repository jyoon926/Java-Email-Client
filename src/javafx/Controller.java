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
import model.LoginModel;

public class Controller implements Initializable
{
    LoginModel lm = new LoginModel();

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
    public void returnToStart(ActionEvent event) throws Exception
    {
        Parent startParent = FXMLLoader.load(getClass().getResource("Start.fxml"));
        Scene start = new Scene(startParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(start);
        window.show();
    }

    @FXML
    public void startLoginAction(ActionEvent event) throws Exception
    {
        Parent loginParent = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene login = new Scene(loginParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(login);
        window.show();
    }

    @FXML
    public void startCreateAccountAction(ActionEvent event) throws Exception
    {
        Parent createAccountParent = FXMLLoader.load(getClass().getResource("CreateAccount.fxml"));
        Scene createAccount = new Scene(createAccountParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(createAccount);
        window.show();
    }

    @FXML
    public void loginAction(ActionEvent event) throws Exception {
        String email = textEmail.getText().toString();
        String password = textPassword.getText().toString();


        System.out.println("2");
        //Change this if statement so that it checks if the account is in the database
        try {
            if (lm.isLogin(email, password)) {
                System.out.println("asdfgt");
                login(event);
            } else {
                System.out.println("aergdfgbsfgdr");
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Failed");
                alert.setHeaderText("Please enter a correct email and/or password");
                alert.showAndWait();
            }
        }
        catch (NullPointerException e){
            e.getStackTrace();
            System.out.println("fdsaasdhfdsgersa");

        }
    }

    public void login(ActionEvent event) {
        try {
            Parent applicationParent = FXMLLoader.load(getClass().getResource("Application.fxml"));
            Scene application = new Scene(applicationParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(application);
            window.show();
            window.setResizable(false);
            System.out.println("sdfa3");
        } catch (IOException e) {
            System.out.println("sdfa");
            e.getStackTrace();
        }
    }

    @FXML
    public void createAccountAction(ActionEvent event) throws Exception
    {
        String name = newName.getText().toString();
        String email = newEmail.getText().toString();
        String password = newPassword.getText().toString();
        String passwordRetype = confirmPassword.getText().toString();
        if (name.length() > 0 && email.length() > 0 && password.length() > 0 && password.equals(passwordRetype))
        {
            //Write code to add the account to the database here
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Welcome to DeppeMail, " + name + ".");
            alert.showAndWait();
            login(event);
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
        if (this.lm.isConnected()) {
            System.out.println("connected");
        }
        else {
            System.out.println("not connected");
        }
    }
}
