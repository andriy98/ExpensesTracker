import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * ManagerTest is the test class for Manager.java
 *
 * @author Andrii
 */
public class ManagerTest {
    /**
     * This method tests addExpense(String date, double amount, String currency, String product)
     * from Manager.java
     */
    @Test
    public void addExpense() {
        Creator creator = new Creator();
        Manager manager = new Manager(creator);
        boolean add = manager.addExpense("2018-07-02", 10, "USD", "Jogurt");
        assertEquals(true, add);
    }

    /**
     * This method tests deleteExpenseByDate(String date) from Manager.java
     */
    @Test
    public void deleteExpenseByDate() {
        Creator creator = new Creator();
        Manager manager = new Manager(creator);
        boolean delete = manager.deleteExpenseByDate("2018-07-02");
        assertEquals(true, delete);
    }
}