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

    public void loginAction(ActionEvent event) throws Exception
    {
        String email = textEmail.getText().toString();
        String password = textPassword.getText().toString();

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


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {

    }
}
