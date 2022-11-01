package VendingMachine.service;

import VendingMachine.dao.PersistenceException;
import VendingMachine.dto.Item;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The {@code ServiceImplTest} class is responsible for
 * testing the ServiceLayerImpl class.
 */
public class ServiceImplTest {
    // Declare ServiceLayer object
    public static ServiceLayer service;

    public ServiceImplTest() {
        ApplicationContext ctx =
                new ClassPathXmlApplicationContext("applicationContext.xml");
        service = ctx.getBean("serviceLayer", ServiceLayer.class);
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of getItem method, of class VendingMachineServiceImpl.
     */
    @Test
    public void testGetItem() throws Exception {
        // ARRANGE
        Item testClone = new Item("Doritos");
        testClone.setItemCost(new BigDecimal("1.23"));
        testClone.setItemQuantity(9);

        // ACT & ASSERT
        Item shouldBeDoritos = service.getItem("Doritos");
        assertNotNull(shouldBeDoritos,
                "Getting item 'shouldBeDoritos' should not be null.");
        assertEquals(testClone, shouldBeDoritos,
                "Item stored in 'shouldBeDoritos' should be Doritos.");
        Item shouldBeNull = service.getItem("Peanuts");
        assertNull(shouldBeNull, "Getting item 'Peanuts' should be null.");
    }

    /**
     * Test of getAllItems method, of class VendingMachineServiceImpl.
     */
    @Test
    public void testGetAllItems() throws Exception {
        // ARRANGE
        Item testClone = new Item("Doritos");
        testClone.setItemCost(new BigDecimal("1.23"));
        testClone.setItemQuantity(9);

        // ACT & ASSERT
        assertEquals(1, service.getAllItems().size(),
                "Should only have 1 item.");
        assertTrue(service.getAllItems().contains(testClone),
                "The 1 item should be Doritos.");
    }

    /**
     * Test of changeInventoryCount method of VendingMachineServiceImpl
     */
    @Test
    public void testChangeInventoryQuantity() throws PersistenceException,
            ItemInventoryException {

        // ARRANGE
        Item testClone = new Item("Doritos");
        testClone.setItemCost(new BigDecimal("1.23"));
        testClone.setItemQuantity(9);

        // ACT & ASSERT
        service.changeInventoryQuantity(testClone, 20);
        Item itemAfterSale = service.getItem("Doritos");
        assertNotNull(testClone,"Item should not be null.");
        assertEquals(20, itemAfterSale.getItemQuantity());
    }

    /**
     * Test of sellItem method, of class VendingMachineServiceImpl.
     */
    @Test
    public void testSellItem() throws PersistenceException,
            InsufficientFundsException,
            ItemInventoryException {
        // ARRANGE
        Item testClone = new Item("Doritos");
        testClone.setItemCost(new BigDecimal("1.23"));
        testClone.setItemQuantity(9);
        BigDecimal testBalance = new BigDecimal("10.00");

        // ACT & ASSERT
        assertEquals(testClone.getItemQuantity(), 9,
                "testClone quantity before sale should be 9.");
        BigDecimal balanceAfterSale = service.sellItem(testBalance, testClone);
        Item result = service.getItem("Doritos");
        assertTrue(testClone.getItemQuantity() >= 0,
                "Item quantity must be greater than 0 to sell item.");
        assertEquals(result.getItemQuantity(), 8,
                "testClone quantity after sale should be 8");
        assertTrue(testBalance.compareTo(testClone.getItemCost()) >= 0,
                "Balance must be greater than or equal to item cost.");
        assertEquals(balanceAfterSale, testBalance.subtract(testClone.getItemCost()),
                "Balance returned should equal the testBalance - itemCost.");
    }
}
