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
import javafx.stage.Stage;

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

    public void startLoginAction(ActionEvent event) throws Exception
    {
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Login.fxml")));
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void startCreateAccountAction(ActionEvent event) throws Exception
    {
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("CreateAccount.fxml")));
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    public void loginAction(ActionEvent event) throws Exception
    {
        String email = textEmail.getText().toString();
        String password = textPassword.getText().toString();

        //Change this if statement so that it checks if the account is in the database
        if (!email.equals("email@gmail.com") || !password.equals("password"))
        {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Failed");
            alert.setHeaderText("Please enter a correct email and/or password");
            alert.showAndWait();
        }
        else
        {
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Application.fxml")));
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            stage.setResizable(false);
        }
    }

    public void createAccountAction(ActionEvent event) throws Exception
    {
        String name = newName.getText().toString();
        String email = newEmail.getText().toString();
        String password = newPassword.getText().toString();
        String passwordRetype = confirmPassword.getText().toString();
        if (password.equals(passwordRetype))
        {
            //Write code to add the account to the database here
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Welcome to DeppeMail, " + name + ". Your email is " + email + " and your password is " + password + ".");
            alert.showAndWait();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("Application.fxml")));
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            stage.setResizable(false);
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
}
