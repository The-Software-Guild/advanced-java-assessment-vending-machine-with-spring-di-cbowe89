package VendingMachine.service;

import VendingMachine.dao.PersistenceException;
import VendingMachine.dto.Item;

import java.math.BigDecimal;
import java.util.List;

public interface ServiceLayer {
    Item getItem(String itemName) throws
            PersistenceException,
            ItemInventoryException;

    List<Item> getAllItems() throws
            PersistenceException;

    Item addItem(Item item) throws
            PersistenceException;

    Item removeItem(Item item) throws
            PersistenceException;

    void changeInventoryQuantity(Item item, int newCount) throws
            PersistenceException;

    BigDecimal sellItem(BigDecimal totalFunds, Item item) throws
            PersistenceException,
            ItemInventoryException,
            InsufficientFundsException;
}
