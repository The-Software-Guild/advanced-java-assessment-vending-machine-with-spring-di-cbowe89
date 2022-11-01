package VendingMachine.dao;

import VendingMachine.dto.Item;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The {@code VendingMachineDaoFileImplTest} class is responsible for
 * testing the VendingMachineDaoFileImpl class.
 */
class VendingMachineDaoFileImplTest {

    // Declare VendingMachineDao object
    public static VendingMachineDao testDao;

    /**
     * No-args constructor for VendingMachineDaoFileImplTest class
     */
    public VendingMachineDaoFileImplTest() {
    }

    @BeforeEach
    void setUp() throws IOException, PersistenceException {
        String testFile = "testItems.txt";
        // Use FileWriter to blank the file
        new FileWriter(testFile);
        testDao = new VendingMachineDaoFileImpl(testFile);

        // Create Item, add to testDao
        Item testItem = new Item("Water",
                new BigDecimal("0.80"), 20);
        testDao.addItem(testItem.getItemName(), testItem);
    }

    @AfterEach
    void tearDown() {
    }

    /**
     * Test of getAllItems method of VendingMachineDaoImpl
     */
    @Test
    void testGetAllItems() throws PersistenceException {
        List<Item> testList = testDao.getAllItems();
        assertFalse(testList.isEmpty(),
                "List is not empty after calling getAllItems().");
    }

    /**
     * Test of getItem method of VendingMachineDaoImpl
     */
    @Test
    void testGetItem() throws PersistenceException {
        // Confirm item is null when not found in file
        Item testItem1 = testDao.getItem("M&Ms");
        assertNull(testItem1,
                "Null: Unable to retrieve M&Ms from testDao.");

        // Confirm item is not null when found in file
        Item testItem2 = testDao.getItem("Water");
        assertNotNull(testItem2,
                "Not Null: Water retrieved from testDao.");
    }

    /**
     * Test of changeInventoryQuantity method of VendingMachineDaoImpl
     */
    @Test
    void testChangeItemQuantity() throws PersistenceException {
        Item water = testDao.getItem("Water");
        int quantityBefore = water.getItemQuantity();
        testDao.changeInventoryQuantity(water,
                water.getItemQuantity()-1);
        int quantityAfter = water.getItemQuantity();
        assertNotEquals(quantityBefore, quantityAfter,
                "Original quantity do not equal quantity after change.");
        assertEquals(quantityBefore, (quantityAfter+1),
                "Original quantity is 1 more than the quantity after change.");
    }
}
