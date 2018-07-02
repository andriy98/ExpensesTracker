import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * CreatorTest is the test class for Creator.java
 *
 * @author Andrii
 */
public class CreatorTest {
    /**
     * This method tests createJsonFile(JSONArray jsonArray) from Creator.java
     *
     * @throws IOException if error while creating the file
     */
    @Test
    public void createJsonFile() throws IOException {
        Creator creator = new Creator();
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(creator.createRow("2018-07-02", 10, "USD", "Jogurt"));
        assertEquals(true, creator.createJsonFile(jsonArray));
    }

    /**
     * This method tests createRow() from Creator.java
     */
    @Test
    public void createRow() {
        Creator creator = new Creator();
        JSONObject first = new JSONObject();
        JSONObject second;
        first.put("date", "2018-07-02");
        first.put("amount", (double) 10);
        first.put("currency", "USD");
        first.put("product", "Jogurt");
        second = creator.createRow("2018-07-02", 10, "USD", "Jogurt");
        assertEquals(first, second);
    }
}