package VendingMachine.dao;

import VendingMachine.dto.Item;

import java.util.List;

public interface VendingMachineDao {

    Item addItem(String itemName, Item item) throws
            PersistenceException;

    List<Item> getAllItems() throws
            PersistenceException;

    Item getItem(String itemName) throws
            PersistenceException;

    Item removeItem(Item item) throws
            PersistenceException;

    Item changeInventoryQuantity(Item item, int newQuantity) throws
            PersistenceException;
}
