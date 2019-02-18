package ic.doc;

import ic.doc.web.HTMLResultPage;
import ic.doc.web.IndexPage;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WebServer {

    public WebServer() throws Exception {
        Server server = new Server(Integer.valueOf(System.getenv("PORT")));

        ServletHandler handler = new ServletHandler();
        handler.addServletWithMapping(new ServletHolder(new Website()), "/*");
        server.setHandler(handler);

        server.start();
    }

    static class Website extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            String query = req.getParameter("q");
            String format = req.getParameter("format");

            if (query == null || query.matches("\\s*")) {
                new IndexPage().writeTo(resp);

            } else {
                String content = new QueryProcessor().process(query);
                if (format.toLowerCase().equals("html") || (content == null)
                  || (content.isEmpty())) {
                    new HTMLResultPage(query, content).writeTo(resp);
                } else {
                    new FileDownload(query).prepareFileDownload(resp,
                      format);
                }
            }
        }

    }

    public static void main(String[] args) throws Exception {
        new WebServer();
    }
}



