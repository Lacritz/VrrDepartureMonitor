package message.urlreader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;


public class URLReader {

    public static void initURLReader() {
        SSLUtilities.trustAllHostnames();
        SSLUtilities.trustAllHttpsCertificates();
    }


    public static String read(URL url) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
        String message = "";
        message = in.lines().reduce("", String::concat);
        in.close();
        return message;
    }
}