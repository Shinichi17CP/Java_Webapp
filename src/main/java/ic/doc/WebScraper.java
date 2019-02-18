package ic.doc;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;


public class WebScraper {

    private String content;

    WebScraper(String query) {
        try {
            Document document =
                Jsoup.connect("https://en.wikipedia.org/wiki/"
                    + query.replaceAll(" ", "_")).get();
            Elements texts = document.select("p");

            String keyword = query.split(" ")[0];
            for (Element e : texts) {
                if (e.text().contains(keyword)) {
                    content = e.text().replaceAll("\\[\\d+\\]", "");
                    if (e.text().contains("may refer to")) {
                        content += getWikiList(document, keyword);
                    }
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("ERROR: Website not found.");
        }
    }

    private String getWikiList(Document document, String keyword) {
        StringBuilder sb = new StringBuilder();
        Elements list = document.select("ul");

        for (Element listElem : list) {
            Elements elements = listElem.children();
            elements.select("a,i,b,q,u,s").unwrap();
            for (Element e : elements) {
                if (e.ownText().contains(keyword)) {
                   sb.append("\n\t\u2022 " + e.ownText());
                }
            }
        }

        return sb.toString();
    }

    public String getContent() {
        return content;
    }

}
