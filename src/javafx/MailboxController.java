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
    private Pane messagePane;
    @FXML
    private Label fromLabel;
    @FXML
    private Label subjectLabel;
    @FXML
    private Label messageText;
    @FXML
    private Button messageButton1;
    @FXML
    private Button messageButton2;
    @FXML
    private Button messageButton3;
    @FXML
    private Button messageButton4;
    @FXML
    private Button messageButton5;

    private String name;
    private String username;
    private String password;
    private int numberOfNewMessages = 0;

    /**
     * Goes back to the Start scene
     * @param event The input from the cancel button on the Login scene
     * @throws Exception
     */
    public void returnToStart(ActionEvent event) throws Exception
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Warning!");
        alert.setHeaderText("All of the messages in your current mailbox will be deleted.");
        alert.showAndWait();
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
     * Displays message in Mailbox
     * @param event The input from the view message button in the Mailbox scene
     * @throws Exception
     */
    public void viewMessage(ActionEvent event) throws Exception
    {
        if (event.getSource() == messageButton1)
        {
            messageButton1.setStyle("-fx-background-color: #1d2737");
            fromLabel.setText("First person");
            subjectLabel.setText("The subject of the first message");
            messageText.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
        }
        if (event.getSource() == messageButton2)
        {
            messageButton2.setStyle("-fx-background-color: #1d2737");
            fromLabel.setText("Second person");
            subjectLabel.setText("The subject of the second message");
            messageText.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
        }
        if (event.getSource() == messageButton3)
        {
            messageButton3.setStyle("-fx-background-color: #1d2737");
            fromLabel.setText("Third person");
            subjectLabel.setText("The subject of the third message");
            messageText.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
        }
        if (event.getSource() == messageButton4)
        {
            messageButton4.setStyle("-fx-background-color: #1d2737");
            fromLabel.setText("Fourth person");
            subjectLabel.setText("The subject of the fourth message");
            messageText.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
        }
        if (event.getSource() == messageButton5)
        {
            messageButton5.setStyle("-fx-background-color: #1d2737");
            fromLabel.setText("Fifth person");
            subjectLabel.setText("The subject of the fifth message");
            messageText.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
        }
    }

    public void btn(ActionEvent event)
    {
        newMessage();
    }

    /**
     * Makes a new message button become visible
     */
    public void newMessage()
    {
        ArrayList<Button> messageButtons = new ArrayList<Button>() {{
            add(messageButton1);
            add(messageButton2);
            add(messageButton3);
            add(messageButton4);
            add(messageButton5);
        }};
        if (numberOfNewMessages < 5)
        {
            Button newMessageButton = messageButtons.get(numberOfNewMessages);
            newMessageButton.setVisible(true);
            numberOfNewMessages++;
        }
        else
        {
            //Error message
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Too many new messages");
            alert.setHeaderText("DeppeMail can only handle 5 messages in the mailbox at once. Sorry.");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {}
}