package ic.doc.web;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class IndexPage implements Page {

    public void writeTo(HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();

        // Header
        writer.println("<html>");
        writer.println("<head><title>Welcome</title></head>");
        writer.println("<body>");

        // Content
        writer.println(
                "<h1>Welcome!!</h1>" +
                        "<p>Enter your query in the box below: " +
                        "<form>" +
                        "<input type=\"text\" name=\"q\" />" +

                      "<p>Select a response format:</p>" +
                      "<div>" +
                      "<input type=\"radio\" id=\"html\" " +
                      "name=\"format\" value=\"html\" checked>" +
                      "<label for=\"HTML\"> HTML </label>" +
                      "</div>" +

                      "<div>" +
                      "<input type=\"radio\" id=\"markdown\" name=\"format\" " +
                    "value=\"markdown\">" +
                      "<label for=\"Markdown\"> Markdown </label>" +
                      "</div>" +

                      "<div>" +
                      "<input type=\"radio\" id=\"pdf\" name=\"format\" value=\"pdf\">" +
                      "<label for=\"PDF\"> PDF</label>" +
                      "</div>" +

                        "<br><br><input type=\"submit\">" +
                        "</form>" +
                        "</p>");

        // Footer
        writer.println("</body>");
        writer.println("</html>");
    }
    
}
