package javafx;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MessageController implements Initializable
{
    @FXML
    private TextField recipients;
    @FXML
    private TextField subject;
    @FXML
    private TextArea message;
    @FXML
    private Button deleteButton;

    public void deleteMessage()
    {
        Stage stage = (Stage) deleteButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {}
}
