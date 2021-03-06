import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Parser is the class which allows to print expenses list from file and
 * get all currencies from list
 *
 * @author Andrii
 */

public class Parser {

    private static final String fileName = "expenses.json";
    private JSONArray expenses;
    private JSONObject raw;

    /**
     * printList() is the method to print expenses list
     *
     * @throws IOException    if error while processing the file
     * @throws ParseException if error while parsing rows from file
     */

    public void printList() throws IOException, ParseException {
        if (new File(fileName).exists()) {
            FileReader fileReader = new FileReader(fileName);
            JSONParser jsonParser = new JSONParser();
            HashSet<String> dates = new HashSet<String>();
            JSONObject fileContent = (JSONObject) jsonParser.parse(fileReader);
            expenses = (JSONArray) fileContent.get("expenses");
            if (expenses.size() > 0) {
                Collections.sort(expenses, (Comparator<JSONObject>) (obj1, obj2) -> {
                    String date1 = String.valueOf(obj1.get("date"));
                    String date2 = String.valueOf(obj2.get("date"));
                    return date1.compareTo(date2);
                });
                for (int i = 0; i < expenses.size(); i++) {
                    raw = (JSONObject) expenses.get(i);
                    String date = String.valueOf(raw.get("date"));
                    if (dates.add(date)) {
                        System.out.println(date);
                        for (int j = 0; j < expenses.size(); j++) {
                            JSONObject jsObj = (JSONObject) expenses.get(j);
                            if (jsObj.get("date").equals(date)) {
                                System.out.print(jsObj.get("product") + " ");
                                System.out.print(jsObj.get("amount") + " ");
                                System.out.println(jsObj.get("currency") + " ");
                            }
                        }
                        System.out.println();
                    }
                }
            } else {
                System.out.println("Your expenses list is empty !");
            }
        } else {
            System.out.println("Your expenses list is empty !");
        }
    }

    /**
     * getCurrenciesFromList() is the method to get name and value of every currency from expenses list
     *
     * @return currencies HashMap which contains key - name of currency;
     * value - value for this currency to exchange
     * @throws IOException    if error while getting all currencies from file
     * @throws ParseException if error while parsing rows from file
     */

    public HashMap<String, Double> getCurrenciesFromList() throws IOException, ParseException {
        expenses = new JSONArray();
        double old;
        String currency;
        HashMap<String, Double> currencies = new HashMap<>();
        if (new File(fileName).exists()) {
            FileReader fileReader = new FileReader(fileName);
            JSONParser jsonParser = new JSONParser();
            JSONObject fileContent = (JSONObject) jsonParser.parse(fileReader);
            expenses = (JSONArray) fileContent.get("expenses");
            if (expenses.size() > 0) {
                for (int i = 0; i < expenses.size(); i++) {
                    raw = (JSONObject) expenses.get(i);
                    currency = String.valueOf(raw.get("currency"));
                    if (currencies.keySet().contains(currency)) {
                        old = currencies.get(currency);
                        currencies.replace(currency, old, old + (Double) raw.get("amount"));
                    } else {
                        currencies.put(currency, (Double) raw.get("amount"));
                    }
                }
            } else {
                System.out.println("Your expenses list is empty !");
            }
        } else {
            System.out.println("Your expenses list is empty !");
        }
        return currencies;
    }

}
