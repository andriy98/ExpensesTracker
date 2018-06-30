import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class Creator {

    private static final String filename = "expenses.json";
    private FileWriter fileWriter;

    public void createJsonFile(JSONArray jsonArray) throws IOException {
        JSONObject main = new JSONObject();
        main.put("expenses",jsonArray);
        try {
            fileWriter = new FileWriter(filename);
            fileWriter.write(main.toString());
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            fileWriter.close();
        }
    }



    public JSONObject createRaw(String date, double amount, String currency, String product) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("date", date);
        jsonObject.put("amount", amount);
        jsonObject.put("currency", currency);
        jsonObject.put("product", product);
        return jsonObject;
    }
}
