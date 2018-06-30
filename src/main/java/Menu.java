import org.json.simple.parser.ParseException;
import java.io.IOException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Menu {
    private Creator creator;
    private Manager manager;
    private Parser parser;

    public Menu(Creator creator, Manager manager, Parser parser) {
        this.creator = creator;
        this.manager = manager;
        this.parser = parser;
    }

    public void showMenu() throws ParseException, IOException {
        System.out.println("Type your command here (to watch all enabled commands - use command help)");
        Scanner scanner = new Scanner(System.in);
        Pattern pattern = Pattern.compile(" ");
        while (true) {
            System.out.print(">");
            String[] values = pattern.split(scanner.nextLine());
            if (values[0].equals("")) {
                continue;
            } else {
                String command = values[0];
                switch (command) {
                    case "add":
                        String date;
                        if (values.length < 5) {
                            System.out.println("Invalid \"add\" command !");
                            System.out.println("Syntax: \"add [date(\"yyyy-mm-dd\")] [amount_of_money] [currency] [name_of_product]\"");
                        } else {
                            date = values[1];
                            if (!isLegalDate(date)) {
                                System.out.println("Entered date is incorrect ! (Format: \"yyyy-mm-dd\")");
                            } else if (!values[2].matches("-?[0-9]+.?[0-9]*")) {
                                System.out.println("Entered amount of money is incorrect !");
                            } else if (!values[3].matches("^[-A-Z]+") || values[3].length() != 3) {
                                System.out.println("Entered currency is incorrect ! (Should have 3 uppercase letters !)");
                            } else if (!values[3].matches("^[-a-zA-Z]+")) {
                                System.out.println("Entered name of product is incorrect !");
                            } else {
                                double amount = Double.parseDouble(values[2]);
                                String currency = values[3];
                                String name = values[4];
                                manager.addExpense(date, amount, currency, name);
                                parser.getList();
                            }
                        }
                        break;
                    case "list":
                        parser.getList();
                        break;
                    case "clear":
                        if (values.length < 2) {
                            System.out.println("Invalid \"clear\" command !");
                            System.out.println("Syntax: \"clear [date(\"yyyy-mm-dd\")]");
                        } else {
                            date = values[1];
                            if (!isLegalDate(date)) {
                                System.out.println("Entered date is incorrect ! (Format: \"yyyy-mm-dd\")");
                            } else {
                                manager.deleteExpenseByDate(date);
                                parser.getList();
                            }
                        }
                        break;
                    case "exit":
                        System.exit(0);
                    default:
                        System.out.println("Invalid command !\nUse help to watch all enabled commands !");
                }
            }
        }
    }

    private static boolean isLegalDate(String s) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        return sdf.parse(s, new ParsePosition(0)) != null;
    }

}
