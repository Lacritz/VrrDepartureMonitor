package message.urlreader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


public class URLReader {

    public static void initURLReader() {
        SSLUtilities.trustAllHostnames();
        SSLUtilities.trustAllHttpsCertificates();
    }


    public static String read(URL url) throws Exception {
    	URLConnection connection = url.openConnection();
    	connection.setConnectTimeout(2000);
    	connection.setReadTimeout(2000);
        connection.setUseCaches(false);
        connection.setDefaultUseCaches(false);
        connection.setRequestProperty("Cache-Control", "no-cache, no-store, must-revalidate");
        connection.setRequestProperty("Pragma", "no-cache");
        connection.setRequestProperty("Expires", "0");
        connection.connect();
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String message = "";
        message = in.lines().reduce("", String::concat);
        in.close();
        return message;
    }
}