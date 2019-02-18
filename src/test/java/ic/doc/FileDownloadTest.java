package ic.doc;

import org.junit.Test;

import java.io.*;
import java.util.Scanner;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import javax.servlet.http.HttpServletResponse;

public class FileDownloadTest {

    HttpServletResponse resp = mock(HttpServletResponse.class);

    private final String query = "Shakespeare";
    private FileDownload fileDownload = new FileDownload(query);

    @Test
    public void invalidQueryReturnsErrorMessageFile() {
        FileDownload fileDownload = new FileDownload("asdfghjkl");
        File tempFile = fileDownload.getTempFile();
        assertThat(getFileContent(tempFile), containsString("Sorry"));
    }

    @Test
    public void validQueryGetsCorrectResult() {
        File tempFile = fileDownload.getTempFile();
        assertThat(getFileContent(tempFile), containsString("playwright"));
    }

    @Test
    public void validQueryGetsResultNotCaseSensitive() {
        FileDownload fileDownload = new FileDownload("asIMOV");
        File tempFile = fileDownload.getTempFile();
        assertThat(getFileContent(tempFile), containsString("science fiction"));
    }

    @Test
    public void setsContentTypeMarkDown() {
        fileDownload.setFileHeader(resp, "markdown");
        verify(resp).setContentType("text/markdown");
    }

    @Test
    public void setsContentTypePDF() {
        fileDownload.setFileHeader(resp, "pdf");
        verify(resp).setContentType("application/pdf");
    }

    private String getFileContent(File file) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                stringBuilder.append(sc.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: File not found!");
        }

        return stringBuilder.toString();
    }
}
