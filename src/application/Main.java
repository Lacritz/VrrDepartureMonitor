package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.*;
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


        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
