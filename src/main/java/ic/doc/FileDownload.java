package ic.doc;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Arrays;
import java.util.List;

import static java.io.File.createTempFile;

@WebServlet("/download")
public class FileDownload extends HttpServlet {

    private String query;
    private String content;
    private File tempFile;

    FileDownload(String query) {
        this.query = query;
        content = new QueryProcessor().process(query);
        try {
        tempFile = createTempFile(query,".md");
        } catch (IOException e) {
            System.out.println("ERROR: IOException encountered!");
        }
        writeToFile();
    }

    public File getTempFile() {
        return tempFile;
    }

    private void writeToFile() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile));
            bw.write("#" + query + "\n");

            if (content == null || content.isEmpty()) {
                bw.write("Sorry we couldn't find a matching result. Please " +
                  "enter again.");
            } else {
                bw.write(content);
            }

            bw.close();
        } catch (IOException e) {
            System.out.println("ERROR: IOException encountered!");
        }
    }

    public void prepareFileDownload(HttpServletResponse resp, String format) throws IOException {
        setFileHeader(resp, format);
        InputStream inputStream;
        
        if (format.equals("markdown")) {
            inputStream = new FileInputStream(tempFile);
        } else {
            inputStream = downloadPdf();
        }
        
        OutputStream outputStream = resp.getOutputStream();
        outputStream.write(inputStream.readAllBytes());
        inputStream.close();
    }

    private Process getPdfProcess() throws IOException {
        List<String> command = Arrays.asList("pandoc",
          "--latex-engine=xelatex", "-s", "-o", query +
            ".pdf", tempFile.getAbsolutePath()) ;
        ProcessBuilder builder = new ProcessBuilder(command);
        return builder.start();
    }

    private InputStream downloadPdf() throws IOException {
	    Process p = getPdfProcess();
        try {
            p.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return new FileInputStream(new File(query + ".pdf"));
    }

    public void setFileHeader(HttpServletResponse resp, String format) {
        String suffix = format.toLowerCase().equals("markdown") ? ".md" : ".pdf";
        resp.setHeader("Content-Transfer-Encoding", "binary");
        resp.setHeader("Content-disposition", "attachment; filename=\""
                + query + suffix + "\"");

        if (format.toLowerCase().equals("markdown")) {
            resp.setContentType("text/markdown");
        } else {
            resp.setContentType("application/pdf");
        }
    }
}
