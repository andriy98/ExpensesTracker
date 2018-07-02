import org.json.simple.parser.ParseException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * ParserTest is the test class for Parser.java
 *
 * @author Andrii
 */
public class ParserTest {
    @Rule
    public Timeout globalTimeout = new Timeout(120);

    /**
     * This method tests printList() from Parser.java
     *
     * @throws IOException    if error while processing the file
     * @throws ParseException if error while parsing rows from file
     */
    @Test
    public void printList() throws IOException, ParseException {
        Parser parser = new Parser();
        parser.printList();
    }

    /**
     * This method tests getCurrenciesFromList() from Parser.java
     *
     * @throws IOException    if error while getting all currencies from file
     * @throws ParseException if error while parsing rows from file
     */
    @Test
    public void getCurrenciesFromList() throws IOException, ParseException {
        Parser parser = new Parser();
        parser.getCurrenciesFromList();
    }
}