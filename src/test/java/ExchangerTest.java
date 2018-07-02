import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.IOException;

/**
 * ExchangerTest is the test class for Exchanger.java
 *
 * @author Andrii
 */
public class ExchangerTest {
    /**
     * This method tests exchange(String currency) from Exchange.java
     */
    @Test(timeout = 1000)
    public void exchange() {
        Parser parser = new Parser();
        Exchanger exchanger = new Exchanger(parser);
        try {
            exchanger.exchange("UAH");
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}