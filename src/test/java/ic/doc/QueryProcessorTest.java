package ic.doc;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class QueryProcessorTest {

    QueryProcessor queryProcessor = new QueryProcessor();

// Extension invalidates this test
//    @Test
//    public void returnsEmptyStringIfCannotProcessQuery() throws Exception {
//        assertThat(queryProcessor.process("test"), is(""));
//    }

    @Test
    public void knowsAboutShakespeare() {
        assertThat(queryProcessor.process("Shakespeare"), containsString("playwright"));
    }

    @Test
    public void knowsAboutAsimov() {
        assertThat(queryProcessor.process("Asimov"), containsString("science fiction"));
    }

    @Test
    public void isNotCaseSensitive() {
        assertThat(queryProcessor.process("shakespeare"), containsString("playwright"));
    }

    @Test
    public void knowsAboutTrang() {
        assertThat(queryProcessor.process("trang"), containsString("mess"));
    }

    @Test
    public void knowsAboutHelen() {
        assertThat(queryProcessor.process("helen"), containsString("hot wings"));
    }

    @Test
    public void knowsAboutAmina() {
        assertThat(queryProcessor.process("amina"), containsString("BTS"));
    }

    @Test
    public void knowsAboutYiming() {
        assertThat(queryProcessor.process("yiming"), containsString("original"));
    }

    @Test
    public void knowsAboutIC() {
        assertThat(queryProcessor.process("Imperial College"),
            containsString("Prince Albert"));
    }

    @Test
    public void multipleWikiResults() {
        assertThat(queryProcessor.process("Queen"),
            containsString("goddess"));
    }
}
