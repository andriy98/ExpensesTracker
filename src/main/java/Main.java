/**
 * Main is the class which allows to start console application "ExpensesTracker"
 *
 * @author Andrii
 */

public class Main {
    public static void main(String[] args) {
        Creator creator = new Creator();
        Parser parser = new Parser();
        Manager manager = new Manager(creator);
        Exchanger exchanger = new Exchanger(parser);
        Menu menu = new Menu(manager, parser, exchanger);
        menu.showMenu();
    }
}