package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/start_medium.fxml"));


        primaryStage.setTitle("VRR_Monitor");
        primaryStage.setScene(new Scene(root));
        //primaryStage.setX(primaryScreenBounds.getMinX());
        //primaryStage.setY(primaryScreenBounds.getMinY());
        //primaryStage.setWidth(primaryScreenBounds.getWidth());
        //primaryStage.setHeight(primaryScreenBounds.getHeight());

        Properties properties = getProperties();

        primaryStage.show();
    }

    private Properties getProperties() throws IOException {
        Properties properties = new Properties();
        BufferedInputStream stream = new BufferedInputStream(new FileInputStream(getClass().getResource
                ("/properties/properties").toString()));
        properties.load(stream);
        stream.close();
        return properties;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
