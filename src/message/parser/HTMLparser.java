package message.parser;

import java.nio.charset.Charset;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 * Created by Timo on 21.04.17.
 */
public class HTMLparser {

    private static HTMLparser htmLparser;

    private HTMLparser() {
    }

    public static HTMLparser getInstance() {
        if (HTMLparser.htmLparser == null) htmLparser = new HTMLparser();
        return htmLparser;
    }

    public String parse(String html, String cssQuery) {
        Document document = Jsoup.parse(html);
        Elements elements = document.select(cssQuery);
        String parsedHTML = "";
        int i = 0;
        for (Element element : elements) {

            parsedHTML += element.ownText() + "#";
            if (i % 4 == 0) parsedHTML += "\n";
            i++;
        }
        return parsedHTML;
    }
}
