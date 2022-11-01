package VendingMachine.service;

import VendingMachine.dao.*;
import VendingMachine.dto.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The {@code ServiceLayerImpl} class is responsible for
 * communicating with the VendingMachineDao and
 * AuditDao
 */
@Component
public class ServiceLayerImpl implements ServiceLayer {
    // Declare VendingMachineDao and AuditDao object
    private final VendingMachineDao vendingMachineDao;
    private final AuditDao auditDao;

    /**
     * No-args constructors creates a ServiceLayerImplObject
     * @throws PersistenceException if error occurs with files
     */
    public ServiceLayerImpl() throws PersistenceException {
        vendingMachineDao = new VendingMachineDaoFileImpl();
        auditDao = new AuditDaoImpl();
    }

    /**
     * Constructor takes in parameters for VendingMachineDao and AuditDao
     * objects, creates a ServiceLayerImpl object
     * @param vendingMachineDao VendingMachineDao object
     * @param auditDao AuditDao object
     */
    @Autowired
    public ServiceLayerImpl(VendingMachineDao vendingMachineDao, AuditDao auditDao) {
        this.vendingMachineDao = vendingMachineDao;
        this.auditDao = auditDao;
    }

    /**
     * Gets an item from the vendingMachineDao object
     * @param itemName name of Item object
     * @return Item
     * @throws PersistenceException if error occurs reading file
     */
    @Override
    public Item getItem(String itemName) throws
            PersistenceException {
        return vendingMachineDao.getItem(itemName);
    }

    /**
     * Gets all items from the vendingMachineDao object
     * @return all Item objects
     * @throws PersistenceException if error occurs reading file
     */
    @Override
    public List<Item> getAllItems() throws PersistenceException {
        return vendingMachineDao.getAllItems()
                .stream()
                .filter(item->item.getItemQuantity()>0)
                .collect(Collectors.toList());
    }

    /**
     * Adds an Item object to the vendingMachineDao, tells auditDao
     * to write entry
     * @param item Item object
     * @return Item added
     * @throws PersistenceException if error occurs writing to file
     */
    @Override
    public Item addItem(Item item) throws PersistenceException {
        auditDao.writeAuditEntry(item.getItemName() + " added.");
        return vendingMachineDao.addItem(item.getItemName(), item);
    }

    /**
     * Removes an Item object to the vendingMachineDao, tells auditDao
     * to write entry
     * @param item Item object
     * @return Item removed
     * @throws PersistenceException if error occurs writing to file
     */
    @Override
    public Item removeItem(Item item) throws PersistenceException {
        auditDao.writeAuditEntry(item.getItemName() + " removed.");
        return vendingMachineDao.removeItem(item);
    }

    /**
     * Tell vendingMachineDao to update an Item object's quantity,
     * tells auditDao to write entry
     * @param item Item object
     * @param newCount new quantity for Item object
     * @throws PersistenceException if error occurs writing to the file
     */
    @Override
    public void changeInventoryQuantity(Item item, int newCount) throws
            PersistenceException {
        auditDao.writeAuditEntry(item.getItemName() + " quantity updated to "
                + newCount + ".");
        vendingMachineDao.changeInventoryQuantity(item, newCount);
    }

    /**
     * Allows vendingMachine to sell an Item. Item quantity and funds
     * are validated, then method tells the auditDao to write and entry,
     * tells the vendingMachineDao to update the Item quantity, and
     * calculates/returns the remaining balance after the purchase
     * @param totalFunds BigDecimal amount for user funds available
     * @param item Item object
     * @return totalFunds after purchase
     * @throws PersistenceException if error occurs with file
     * @throws ItemInventoryException if item quantity is < 1
     * @throws InsufficientFundsException if funds are not enough
     * to purchase Item
     */
    @Override
    public BigDecimal sellItem(BigDecimal totalFunds, Item item) throws
            PersistenceException,
            ItemInventoryException,
            InsufficientFundsException {
        // Validate that item quantity is 1 or more
        validateItemQuantity(item.getItemName());

        // Validate that totalFunds are greater than itemCost
        validateSufficientFunds(totalFunds, item.getItemCost());

        // Write audit entry
        // Allow dao to sell item: subtract 1 from quantity
        // and subtract item cost from totalFunds
        auditDao.writeAuditEntry("1 " + item.getItemName() + " sold.");
        changeInventoryQuantity(item, item.getItemQuantity()-1);

        // Return totalFunds minus itemCost
        return totalFunds.subtract(item.getItemCost());
    }

    /**
     * Validates that Item quantity is >= 1
     * @param itemName name of Item
     * @throws ItemInventoryException if quantity is < 1
     * @throws PersistenceException if error occurs with file
     */
    private void validateItemQuantity (String itemName) throws
            ItemInventoryException, PersistenceException {
        // Get Item
        Item toValidate = vendingMachineDao.getItem(itemName);
        // Throw ItemInventoryException if quantity is invalid
        if (toValidate.getItemQuantity() < 1) {
            throw new ItemInventoryException(
                    "ERROR: " + itemName +
                            " Unavailable! Quantity is less than 1.");
        }
    }

    /**
     * Validates that balance is greater than the Item cost
     * @param balance user funds
     * @param itemCost cost of Item
     * @throws InsufficientFundsException if balance is less than cost
     */
    private void validateSufficientFunds (BigDecimal balance, BigDecimal itemCost)
            throws InsufficientFundsException {
        if (itemCost.compareTo(balance) > 0) {
            throw new InsufficientFundsException("ERROR: $" + balance +
                    " is insufficient to purchase selected item.");
        }
    }
}
