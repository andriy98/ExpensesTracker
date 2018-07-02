import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

/**
 * Creator is the class which allows to create separate row and file of expenses
 *
 * @author Andrii
 */
public class Creator {
    private static final String filename = "expenses.json";
    private FileWriter fileWriter;

    /**
     * createJsonFile(JSONArray jsonArray) is the method which allows to create file to store array of expenses
     *
     * @param jsonArray JSONArray array of expenses which we write to file
     * @return boolean true - if file was successfully created
     * @throws IOException if error while writing to the file
     */
    public boolean createJsonFile(JSONArray jsonArray) throws IOException {
        JSONObject main = new JSONObject();
        main.put("expenses", jsonArray);
        try {
            fileWriter = new FileWriter(filename);
            fileWriter.write(main.toString());
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            fileWriter.close();
        }
        return new File(filename).exists();
    }

    /**
     * createRow(String date, double amount, String currency, String product) is the method which
     * allows to create expense object (row of expense array)
     *
     * @param date     String date of the expense creation
     * @param amount   double amount of the expense
     * @param currency String currency of the expense
     * @param product  String product name of the expense
     * @return jsonObject JSONObject object which contains all this information
     */
    public JSONObject createRow(String date, double amount, String currency, String product) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("date", date);
        jsonObject.put("amount", amount);
        jsonObject.put("currency", currency);
        jsonObject.put("product", product);
        return jsonObject;
    }
}
