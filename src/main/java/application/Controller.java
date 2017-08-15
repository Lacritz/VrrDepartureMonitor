package application;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import message.parser.HTMLparser;
import message.urlreader.URLReader;


public class Controller {
	private static final int FIRSTSTOP = 1, SECONDSTOP = 2, THIRDSTOP = 3, FOURTHSTOP = 4, UPDATEINTERVALL = 2000, TIMEUPDATEINTERVALL = 250;
	private static final Color TEXTCOLOR = Color.rgb(0, 255, 0);
	private static final DateFormat DATEFORMAT = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	public static final boolean __DEBUG = false;
    @FXML TextFlow textFlowStop1, textFlowStop2, textFlowStop3, textFlowStop4;
    @FXML TextFlow textFlowLine1, textFlowLine2, textFlowLine3, textFlowLine4;
    @FXML TextFlow textFlowTime1, textFlowTime2, textFlowTime3, textFlowTime4;
    
    @FXML TextFlow dateFlow;
    @FXML Text headingStop1, headingStop2, headingStop3, headingStop4;
    private final Text timeText = new Text(), 
    		stop1LineText = new Text(),
    		stop1StopText = new Text(),
			stop1TimeText = new Text(),
			stop2LineText = new Text(),
    		stop2StopText = new Text(),
			stop2TimeText = new Text(),
			stop3LineText = new Text(),
    		stop3StopText = new Text(),
			stop3TimeText = new Text(),
			stop4LineText = new Text(),
    		stop4StopText = new Text(),
			stop4TimeText = new Text();
    private final Text[] tarray = {	stop1LineText, 
			stop1StopText,
			stop1TimeText,
			stop2LineText, 
			stop2StopText,
			stop2TimeText,
			stop3LineText, 
			stop3StopText,
			stop3TimeText,
			stop4LineText, 
			stop4StopText,
			stop4TimeText,
			timeText	};
    private final Properties properties = getProperties();
    private final Font font = Font.loadFont(getClass().getResource("/font/VRRR.ttf").toString(), 50);
    @FXML
    public void initialize() throws IOException {
    	URLReader.initURLReader();
    	setUpHeadlines();
        setUpTexts();
        setUpThread();
    }
    
    private void setUpHeadlines() {
    	
        headingStop1.setText(properties.getProperty("firstStop_Name") + ":");
        headingStop1.setFont(font);

        headingStop2.setText(properties.getProperty("secondStop_Name") + ":");
        headingStop2.setFont(font);

        headingStop3.setText(properties.getProperty("thirdStop_Name") + ":");
        headingStop3.setFont(font);

        headingStop4.setText(properties.getProperty("fourthStop_Name") + ":");
        headingStop4.setFont(font);
        
    	
    }
    
    private void setUpTexts() {
    	
        for (Text t : tarray) {
            t.setFont(font);
            t.setFill(TEXTCOLOR);
        }
    	dateFlow.getChildren().add(timeText);
        textFlowLine1.getChildren().addAll(stop1LineText);
        textFlowStop1.getChildren().addAll(stop1StopText);
        textFlowTime1.getChildren().addAll(stop1TimeText);
        textFlowLine2.getChildren().addAll(stop2LineText);
        textFlowStop2.getChildren().addAll(stop2StopText);
        textFlowTime2.getChildren().addAll(stop2TimeText);
        textFlowLine3.getChildren().addAll(stop3LineText);
        textFlowStop3.getChildren().addAll(stop3StopText);
        textFlowTime3.getChildren().addAll(stop3TimeText);
        textFlowLine4.getChildren().addAll(stop4LineText);
        textFlowStop4.getChildren().addAll(stop4StopText);
        textFlowTime4.getChildren().addAll(stop4TimeText);
    }

    private void setUpThread() {
        Runnable task = () -> {
            try {
                while (true) {
                    loop();
                    Thread.sleep(UPDATEINTERVALL);
                }
            } catch (Exception e) {
                if(__DEBUG)e.printStackTrace();
            }
        };
        Runnable timeTask = () -> {
            try {
                while (true) {
                	updateTime();
                    Thread.sleep(TIMEUPDATEINTERVALL);
                }
            } catch (Exception e) {
                if(__DEBUG)e.printStackTrace();
            }
        };
        Thread t = new Thread(task);
        t.start();
        Thread t2 = new Thread(timeTask);
        t2.start();
    }

    private void loop() throws IOException {
        setUpTextOnTextFlow(stop1LineText, stop1StopText, stop1TimeText, FIRSTSTOP);
        setUpTextOnTextFlow(stop2LineText, stop2StopText, stop2TimeText, SECONDSTOP);
        setUpTextOnTextFlow(stop3LineText, stop3StopText, stop3TimeText, THIRDSTOP);
        setUpTextOnTextFlow(stop4LineText, stop4StopText, stop4TimeText, FOURTHSTOP);
    }


    public void updateTime() {
    	Date date = new Date();
    	Platform.runLater(new Runnable() {
			public void run() {
				timeText.setText(DATEFORMAT.format(date));
			}
		});
    }
    public void setUpTextOnTextFlow(Text line, Text flow, Text time, int stop) throws
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
        String[] text = optimizeLayout(HTMLparser.parse
        		(URLReader.read
        				(new URL(properties.getProperty
        						(property))), "div"));
        if(!text[2].contains("min")) {
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
        final String[] finalText = text;
        Platform.runLater(new Runnable() {
			public void run() {
				line.setText(finalText[0]);
		        flow.setText(finalText[1]);
		        time.setText(finalText[2]);
		        
			}
		});
    }


    private String[] optimizeLayout(String s) {
        String out = Charset.forName("UTF-8").decode(Charset.defaultCharset().encode(s)).toString();

        String line = "";
        String stop = "";
        String time = "";
        for (String string : out.split("\n")) {
            try {
            	final String[] lineStopTime = string.split("#");
                line += lineStopTime[1] + "\n";
                if (!(lineStopTime[2].length() <= 17)) {
                    stop += lineStopTime[2].substring(0, 17) + "\n";
                } else {
                    stop += lineStopTime[2] + "\n";
                }
                time += lineStopTime[3] + "\n";
            } catch (ArrayIndexOutOfBoundsException e) {
                continue;
            }
        }

        String[] text = new String[3];
        text[0] = line;
        text[1] = stop;
        text[2] = time;



        
        if(__DEBUG) {
        	System.out.println(text[0]);
        	System.out.println(text[1]);
        	System.out.println(text[2]);
        }
        return text;
    }


    private Properties getProperties(){
        Properties properties = new Properties();
        BufferedInputStream stream = new BufferedInputStream(this.getClass().getResourceAsStream
                ("/properties/properties.prop"));
        try {
        	properties.load(new InputStreamReader(stream, Charset.forName("UTF-8")));
        	stream.close();
        }catch(Exception e) {
        	if(__DEBUG)e.printStackTrace();
        }
        return properties;
    }
}
