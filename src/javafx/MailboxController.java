package javafx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.DBAccess;

import javax.swing.*;

/**
 * Controls Application.fxml
 * @author Jacob Yoon
 */
public class MailboxController implements Initializable
{
    @FXML
    private Button composeButton;
    @FXML
    private Button checkButton;
    @FXML
    private Label label;

    public MailboxController()
    {}

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
    public void checkEmail(ActionEvent event) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("Mailbox.fxml"));
        Stage stage = new Stage();
        stage.setTitle("New Message");
        stage.setScene(new Scene(root, 400, 500));
        stage.show();
        Controller controller = new Controller();
        String messages = "No Messages!";
        if (MailSendReceive.receiveMessage(controller.getUsername()).size() > 0)
        {
            for (int i = 0; i < (MailSendReceive.receiveMessage(controller.getUsername())).size(); i++)
            {
                messages = MailSendReceive.receiveMessage(controller.getUsername()).get(i) + "/n";
            }
        }
        label.setText(messages);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {}
}