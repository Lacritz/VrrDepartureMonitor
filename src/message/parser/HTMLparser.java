package message.parser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.NodeList;


/**
 * Created by Timo on 21.04.17.
 */
public class HTMLparser {

    public String parse(String html, String cssQuery) {
        Document document = Jsoup.parse(html);
        Elements elements = document.select(cssQuery);
        String parsedHTML = "";
        for (Element element : elements) {
            parsedHTML += element.ownText();
        }
        return parsedHTML;
    }
}
