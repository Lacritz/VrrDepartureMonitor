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
	private static final int FIRSTSTOP = 1, SECONDSTOP = 2, THIRDSTOP = 3, FOURTHSTOP = 4, UPDATEINTERVALL = 2000;
	public static final boolean __DEBUG = false;
    @FXML TextFlow textFlowStop1, textFlowStop2, textFlowStop3, textFlowStop4;
    @FXML TextFlow textFlowLine1, textFlowLine2, textFlowLine3, textFlowLine4;
    @FXML TextFlow textFlowTime1, textFlowTime2, textFlowTime3, textFlowTime4;

    @FXML Text headingStop1, headingStop2, headingStop3, headingStop4;
    @FXML ToolBar toolBar;
    Properties properties;
    Font font;

    @FXML
    public void initialize() throws IOException {
    	URLReader.InitURLReader();
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
                    Thread.sleep(UPDATEINTERVALL);
                }
            } catch (Exception e) {
                if(__DEBUG)e.printStackTrace();
            }
        };
        Thread t = new Thread(task);
        t.start();
    }

    private void loop(Properties properties) throws IOException {
        setUpTextOnTextFlow(textFlowLine1, textFlowStop1, textFlowTime1, FIRSTSTOP);
        setUpTextOnTextFlow(textFlowLine2, textFlowStop2, textFlowTime2, SECONDSTOP);
        setUpTextOnTextFlow(textFlowLine3, textFlowStop3, textFlowTime3, THIRDSTOP);
        setUpTextOnTextFlow(textFlowLine4, textFlowStop4, textFlowTime4, FOURTHSTOP);
    }


    public void setUpTextOnTextFlow(TextFlow line, TextFlow flow, TextFlow time, int stop) throws
            IOException {
        String property;
        switch (stop) {
            case FIRSTSTOP:
                property = "firstStop_URL";
                break;
            case SECONDSTOP:
                property = "secondStop_URL";
                break;
            case THIRDSTOP:
                property = "thirdStop_URL";
                break;
            case FOURTHSTOP:
                property = "fourthStop_URL";
                break;
            default:
                property = "";
                break;
        }
        Text[] text = optimizeLayout(HTMLparser.parse
        		(URLReader.read
        				(new URL(properties.getProperty
        						(property))), "div"));
        if(!text[2].getText().contains("min")) {
        	switch (stop) {
            case FIRSTSTOP:
                property = "firstReplacement_URL";
                break;
            case SECONDSTOP:
                property = "secondReplacement_URL";
                break;
            case THIRDSTOP:
                property = "thirdReplacement_URL";
                break;
            case FOURTHSTOP:
                property = "fourthReplacement_URL";
                break;
            default:
                property = "";
                break;
        }
        	text = optimizeLayout(HTMLparser.parse
            		(URLReader.read
            				(new URL(properties.getProperty
            						(property))), "div"));
        }

        final Text[] finaltext = text;
        Platform.runLater(() -> {
            line.getChildren().clear();
            line.getChildren().addAll(finaltext[0]);

            flow.getChildren().clear();
            flow.getChildren().addAll(finaltext[1]);

            time.getChildren().clear();
            time.getChildren().addAll(finaltext[2]);
        });
    }


    private Text[] optimizeLayout(String s) {
        String out = Charset.forName("UTF-8").decode(Charset.defaultCharset().encode(s)).toString();

        String line = "";
        String stop = "";
        String time = "";
        for (String string : out.split("\n")) {
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



        text[0].setFill(Color.WHITE);
        for (Text t : text) {
            t.setFont(font);
            t.setFill(Color.rgb(0, 255, 1));
        }
        if(__DEBUG) {
        	System.out.println(text[0].getText());
        	System.out.println(text[1].getText());
        	System.out.println(text[2].getText());
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
