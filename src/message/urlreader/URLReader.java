package message.urlreader;

import message.parser.HTMLparser;

import java.net.*;
import java.io.*;

public class URLReader {
    private static URLReader urlReader;

    private URLReader() {
    }

    public static URLReader getInstance() {
        if (URLReader.urlReader == null) urlReader = new URLReader();
        return urlReader;
    }

    public String read(URL url) throws IOException {

        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

        String inputLine;
        String message = "";
        while ((inputLine = in.readLine()) != null) {
            message += inputLine;
        }

        in.close();
        return message;
    }
}