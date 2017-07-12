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
    @FXML TextFlow textFlowLine1, textFlowLine2, textFlowLine3, textFlowLine4;
    @FXML TextFlow textFlowTime1, textFlowTime2, textFlowTime3, textFlowTime4;

    @FXML Text headingStop1, headingStop2, headingStop3, headingStop4;
    @FXML ToolBar toolBar;
    Properties properties;
    Font font;

    @FXML
    public void initialize() throws IOException {
        font = Font.loadFont(getClass().getResource("/font/VRRR.ttf").toString(), 50);
        properties = getProperties();
        headingStop1.setText(properties.getProperty("firstStop_Name") + ":");
        headingStop1.setFont(font);

        headingStop2.setText(properties.getProperty("secondStop_Name") + ":");
        headingStop2.setFont(font);

        headingStop3.setText(properties.getProperty("thirdStop_Name") + ":");
        headingStop3.setFont(font);

        headingStop4.setText(properties.getProperty("fourthStop_Name") + ":");
        headingStop4.setFont(font);
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
        setUpTextOnTextFlow(textFlowLine1, textFlowStop1, textFlowTime1, 1);
        setUpTextOnTextFlow(textFlowLine2, textFlowStop2, textFlowTime2, 2);
        setUpTextOnTextFlow(textFlowLine3, textFlowStop3, textFlowTime3, 3);
        setUpTextOnTextFlow(textFlowLine4, textFlowStop4, textFlowTime4, 4);
    }


    public void setUpTextOnTextFlow(TextFlow line, TextFlow flow, TextFlow time, int stop) throws
            IOException {
        String property;
        switch (stop) {
            case 1:
                property = "firstStop_URL";
                break;
            case 2:
                property = "secondStop_URL";
                break;
            case 3:
                property = "thirdStop_URL";
                break;
            case 4:
                property = "fourthStop_URL";
                break;
            default:
                property = "";
                break;
        }
        Text[] text = optimizeLayout(HTMLparser.getInstance()
                .parse(URLReader.getInstance()
                        .read(new URL(properties
                                .getProperty(property))), "div"));


        Platform.runLater(() -> {
            line.getChildren().clear();
            line.getChildren().addAll(text[0]);

            flow.getChildren().clear();
            flow.getChildren().addAll(text[1]);

            time.getChildren().clear();
            time.getChildren().addAll(text[2]);
        });
    }


    private Text[] optimizeLayout(String s) {
        String out = Charset.forName("UTF-8").decode(Charset.defaultCharset().encode(s)).toString();

        String line = "";
        String stop = "";
        String time = "";
        for (String string : s.split(System.lineSeparator())) {
            try {
                line += string.split("#")[1] + "\n";
                if (!(string.split("#")[2].length() <= 17)) {
                    stop += string.split("#")[2].substring(0, 17) + "\n";
                } else {
                    stop += string.split("#")[2] + "\n";
                }
                time += string.split("#")[3] + "\n";
            } catch (ArrayIndexOutOfBoundsException e) {
                continue;
            }
        }

        Text[] text = new Text[3];
        text[0] = new Text(line);
        text[1] = new Text(stop);
        text[2] = new Text(time);

        System.out.println(stop);

        text[0].setFill(Color.WHITE);
        for (Text t : text) {
            t.setFont(font);
            t.setFill(Color.rgb(0, 255, 0));
        }
        return text;
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
