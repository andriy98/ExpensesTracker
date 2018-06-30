import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Manager {
    private static final String filename = "expenses.json";
    private Creator creator;
    private JSONObject jsObj;
    private JSONArray expenses;


    public Manager(Creator creator) {
        this.creator = creator;
    }

    public void addExpense(String date, double amount, String currency, String product){
        expenses = new JSONArray();
        try{
            if (new File(filename).exists()){
                expenses = getArray();
                expenses.add(creator.createRaw(date, amount, currency,product));
                creator.createJsonFile(expenses);
            }else {
                expenses.add(creator.createRaw(date, amount, currency,product));
                creator.createJsonFile(expenses);
            }
        }catch (IOException e){
            System.out.println("Error !");
        }
    }

    public void deleteExpenseByDate(String date){
        JSONArray jsonArray = new JSONArray();
        boolean check = false;
        expenses = getArray();
        JSONObject object;
        for (int i = 0; i < expenses.size(); i++) {
            object = (JSONObject) expenses.get(i);
            if (!object.get("date").equals(date)){
                jsonArray.add(object);
            }else {
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
    }

    private JSONArray getArray(){
        JSONArray array = new JSONArray();
        JSONParser jsonParser = new JSONParser();
        try {
            FileReader fileReader = new FileReader(filename);
            if (new File(filename).exists()) {
                jsObj = (JSONObject) jsonParser.parse(fileReader);
                array = (JSONArray) jsObj.get("expenses");
            }
        }catch (IOException | ParseException e){
            System.out.println("Error !");
        }
        return array;
    }
}
