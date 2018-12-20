package javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Runs the Start fxml application
 */
public class Main extends Application
{

    public static void main(String[] args)
    {
        //DBAccess.startConnection();
        launch(args);
    }

    /**
     * Sets scene to Start fxml application
     * @param primaryStage The application window/scene
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("Start.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}