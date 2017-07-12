package application;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ToolBar;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import message.parser.HTMLparser;
import message.urlreader.URLReader;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Properties;


public class Controller {
    @FXML TextFlow textFlowStop1, textFlowStop2, textFlowStop3, textFlowStop4;
    @FXML Text headingStop1, headingStop2, headingStop3, headingStop4;
    @FXML ToolBar toolBar;
    Properties properties;

    @FXML
    public void initialize() throws IOException {
        properties = getProperties();
        headingStop1.setText(properties.getProperty("firstStop_Name") + ":");
        headingStop2.setText(properties.getProperty("secondStop_Name") + ":");
        headingStop3.setText(properties.getProperty("thirdStop_Name") + ":");
        headingStop4.setText(properties.getProperty("fourthStop_Name") + ":");
        setUpThread();
    }

    private void setUpThread() {
        Runnable task = () -> {
            try {
                while (true) {
                    loop(properties);
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };
        Thread t = new Thread(task);
        t.start();
    }

    private void loop(Properties properties) throws IOException {

        Text text1 = new Text(optimizeLayout(
                HTMLparser.getInstance()
                        .parse(URLReader.getInstance()
                                .read(new URL(properties
                                        .getProperty("firstStop_URL") + "&backend=db")), "div")));
        text1.setFill(Color.WHITE);
        text1.setFont(Font.font("System", 24));
        Platform.runLater(() -> {
            textFlowStop1.getChildren().clear();
            textFlowStop1.getChildren().addAll(text1);
        });

        Text text2 = new Text(optimizeLayout(
                HTMLparser.getInstance()
                        .parse(URLReader.getInstance()
                                .read(new URL(properties
                                        .getProperty("secondStop_URL") + "&backend=db")), "div")));
        text2.setFill(Color.WHITE);
        text2.setFont(Font.font("System", 24));
        Platform.runLater(() -> {
            textFlowStop2.getChildren().clear();
            textFlowStop2.getChildren().addAll(text2);
        });


        Text text3 = new Text(optimizeLayout(
                HTMLparser.getInstance()
                        .parse(URLReader.getInstance()
                                .read(new URL(properties
                                        .getProperty("thirdStop_URL"))), "div")));
        text3.setFill(Color.WHITE);
        text3.setFont(Font.font("System", 24));
        Platform.runLater(() -> {
            textFlowStop3.getChildren().clear();
            textFlowStop3.getChildren().addAll(text3);
        });


        Text text4 = new Text(optimizeLayout(
                HTMLparser.getInstance()
                        .parse(URLReader.getInstance()
                                .read(new URL(properties
                                        .getProperty("fourthStop_URL"))), "div")));
        text4.setFill(Color.WHITE);
        text4.setFont(Font.font("System", 24));
        Platform.runLater(() -> {
            textFlowStop4.getChildren().clear();
            textFlowStop4.getChildren().addAll(text4);
        });
    }


    private String optimizeLayout(String s) {
        String out = Charset.forName("UTF-8").decode(Charset.defaultCharset().encode(s)).toString();
        System.out.println(out);
        return out;
    }


    private Properties getProperties() throws IOException {
        Properties properties = new Properties();
        BufferedInputStream stream = new BufferedInputStream(this.getClass().getResourceAsStream
                ("/properties/properties.prop"));
        properties.load(new InputStreamReader(stream, Charset.forName("UTF-8")));
        stream.close();
        return properties;
    }

}
