import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Manager is the class which allows to add expenses to list and remove expenses from list by date
 *
 * @author Andrii
 */
public class Manager {
    private static final String filename = "expenses.json";
    private Creator creator;
    private JSONObject jsObj;
    private JSONArray expenses;

    /**
     * Creates Manager object with given parameters
     *
     * @param creator object which allows to create file of expenses
     */
    public Manager(Creator creator) {
        this.creator = creator;
    }

    /**
     * addExpense(String date, double amount, String currency, String product) is the method which
     * allows to add expense to expenses list
     *
     * @param date     String date of the expense creation
     * @param amount   double amount of the expense
     * @param currency String currency of the expense
     * @param product  String product name of the expense
     * @return boolean true if expense was successfully added
     */
    public boolean addExpense(String date, double amount, String currency, String product) {
        expenses = new JSONArray();
        JSONObject newRecord;
        newRecord = creator.createRow(date, amount, currency, product);
        try {
            if (new File(filename).exists()) {
                expenses = getArray();
                expenses.add(newRecord);
            } else {
                expenses.add(newRecord);
            }
            creator.createJsonFile(expenses);
        } catch (IOException e) {
            System.out.println("Error w !");
        }
        return expenses.contains(newRecord);
    }

    /**
     * deleteExpenseByDate(String date) is the method which allows delete expense of entered date from the list
     *
     * @param date String date of the expense creation
     * @return boolean true - if expense was successfully deleted
     */
    public boolean deleteExpenseByDate(String date) {
        JSONArray jsonArray = new JSONArray();
        boolean check = false;
        expenses = getArray();
        JSONObject object;
        for (int i = 0; i < expenses.size(); i++) {
            object = (JSONObject) expenses.get(i);
            if (!object.get("date").equals(date)) {
                jsonArray.add(object);
            } else {
                check = true;
            }
        }
        if (!check) {
            System.out.println("There are no expenses with this date !");
        }
        try {
            creator.createJsonFile(jsonArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return check;
    }

    /**
     * getArray() is the method which allows to get array of expenses from file
     *
     * @return array JSONArray array of expenses from file
     */
    private JSONArray getArray() {
        JSONArray array = new JSONArray();
        JSONParser jsonParser = new JSONParser();
        try {
            FileReader fileReader = new FileReader(filename);
            if (new File(filename).exists()) {
                jsObj = (JSONObject) jsonParser.parse(fileReader);
                array = (JSONArray) jsObj.get("expenses");
            }
        } catch (IOException | ParseException e) {
            System.out.println("Error !");
        }
        return array;
    }
}
