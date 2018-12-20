package javafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controls the NewMessage fxml application/scene
 * @author Jacob Yoon
 * @version 12.17.18
 */
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

    /**
     * Deletes the new message and returns to the mailbox
     * @param event The input from the delete button
     */
    public void deleteMessage(ActionEvent event) throws IOException
    {
        Parent startParent = FXMLLoader.load(getClass().getResource("Application.fxml"));
        Scene start = new Scene(startParent);
        Stage window = (Stage)((Node) event.getSource()).getScene().getWindow();
        window.setScene(start);
        window.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {}
}