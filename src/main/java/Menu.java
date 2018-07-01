import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * Menu is the class which prints menu, reads commands, checks this commands for correctness,
 * and prints the results
 *
 * @author Andrii
 */
public class Menu {
    private Manager manager;
    private Parser parser;
    private Exchanger exchanger;

    /**
     * Creates a new Menu object with given parameters
     *
     * @param manager   object which allows to change expenses list
     * @param parser    object which allows to read expenses list
     * @param exchanger object which allows to exchange all currencies from list to one currency
     */
    public Menu(Manager manager, Parser parser, Exchanger exchanger) {
        this.manager = manager;
        this.parser = parser;
        this.exchanger = exchanger;
    }

    /**
     * showMenu() is the method which read entered command and print the results of this command
     */
    public void showMenu() {
        System.out.println("Type your command here (to watch all enabled commands - use command help)");
        Scanner scanner = new Scanner(System.in);
        Pattern pattern = Pattern.compile(" ");
        try {
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
                                    System.out.println(name.matches("(.*)\""));
                                    if (name.charAt(0) == '“' || name.charAt(0) == '"') {
                                        name = "";
                                        for (int i = 4; i < values.length; i++) {
                                            name = name + values[i] + " ";
                                        }
                                        name = name.trim();
                                    }
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
                        case "total":
                            exchanger.exchange();
                            break;
                        case "exit":
                            System.exit(0);
                        default:
                            System.out.println("Invalid command !\nUse help to watch all enabled commands !");
                    }
                }
            }
        } catch (IOException | ParseException e) {
            System.out.println("Error !");
        }
    }

    /**
     * isLegalDate(String date) is the method which check entered date
     *
     * @param date String entered date, which we checked for correctness
     * @return boolean true - if date is correct; false - if entered date is incorrect
     */
    private static boolean isLegalDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        return sdf.parse(date, new ParsePosition(0)) != null;
    }

}
