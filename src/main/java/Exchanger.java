import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Set;

/**
 * Exchanger is the class which allows to download the latest currency rates from fixer.io,
 * exchange all currencies from file to one currency, and show total amount of all expenses
 *
 * @author Andrii
 */

public class Exchanger {
    private static final String address = "http://data.fixer.io/api/latest?access_key=a77a10c34cd8b5713b1de619465de5a9";
    private Parser parser;

    /**
     * Creates a new Exchanger object with given parameter
     *
     * @param parser Parser object which allows to work with expenses list
     */
    public Exchanger(Parser parser) {
        this.parser = parser;
    }

    /**
     * getExchangeValues() is the method for obtaining exchange rate for EUR
     *
     * @return values HashMap which contains key - name of currency; value - exchange rate for this currency
     */
    private HashMap<String, Double> getExchangeValues() {
        HashMap<String, Double> values = new HashMap<>();
        try {
            URL url = new URL(address);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            Set<String> hashSet = parser.getCurrencies().keySet();
            if (content.length() > 0) {
                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject = (JSONObject) jsonParser.parse(String.valueOf(content));
                JSONObject rates = (JSONObject) jsonObject.get("rates");
                for (String item : hashSet) {
                    if (rates.get(item) != null) {
                        values.put(item, Double.parseDouble(String.valueOf(rates.get(item))));
                    } else {
                        System.out.println("Value for exchange of this currency: \"" + item + "\" was not found");
                    }
                }
            }
        } catch (IOException | ParseException | NullPointerException e) {
            System.out.println("Error while loading currency rates !");
        }
        return values;
    }

    /**
     * exchange() is the method of currency exchange from the list for one currency, and show total amount
     * of all expenses
     *
     * @throws IOException    if error while getting all currencies from file
     * @throws ParseException if error while parsing rows from file
     */
    public void exchange() throws IOException, ParseException {
        HashMap<String, Double> amounts = parser.getCurrencies();
        HashMap<String, Double> rates = getExchangeValues();
        double totalValue = 0;
        Set<String> cur = rates.keySet();
        for (String item : cur) {
            totalValue = totalValue + (amounts.get(item) / rates.get(item));
        }
        System.out.println(String.format("%(.2f", totalValue) + " EUR");
    }


    /**
     * Method for obtaining exchange rate for the currency (baseCurrency)
     *
     * @param baseCurrency String name of the currency according to which we download exchange rates
     * @return values HashMap which contains key - name of currency; value - exchange rate for this currency
     */
    /*
    private HashMap<String, Double> getExchangeValues(String baseCurrency) {
        HashMap<String, Double> values = new HashMap<>();
        try {
            URL url = new URL(address+baseCurrency);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            Set<String> hashSet = parser.getCurrencies().keySet();
            if (content.length() > 0) {
                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject = (JSONObject) jsonParser.parse(String.valueOf(content));
                JSONObject rates = (JSONObject) jsonObject.get("rates");
                for (String item : hashSet) {
                    if (rates.get(item) != null) {
                        values.put(item, Double.parseDouble(String.valueOf(rates.get(item))));
                    } else {
                        System.out.println("Value for exchange of this currency: \"" + item + "\" was not found");
                    }
                }
            }
        } catch (IOException | ParseException | NullPointerException e) {
            System.out.println("Error !");
        }
        return values;
    }
*/


}


