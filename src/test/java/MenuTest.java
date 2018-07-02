import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.NoSuchElementException;

/**
 * MenuTest is the test class for Menu.java
 *
 * @author Andrii
 */
public class MenuTest {
    @Rule
    public Timeout globalTimeout = new Timeout(200);

    /**
     * This method tests showMenu() from Menu.java
     */
    @Test
    public void showMenu() {
        Parser parser = new Parser();
        Creator creator = new Creator();
        Manager manager = new Manager(creator);
        Exchanger exchanger = new Exchanger(parser);
        Menu menu = new Menu(manager, parser, exchanger);
        String command = "add 2018-07-02 10 USD Jogurt";
        InputStream stream = new ByteArrayInputStream(command.getBytes(StandardCharsets.UTF_8));
        System.setIn(stream);
        try {
            menu.showMenu();
        } catch (NoSuchElementException e) {
            System.out.println("The test is over");
        }
    }
}