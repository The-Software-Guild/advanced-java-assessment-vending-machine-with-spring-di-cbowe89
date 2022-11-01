package VendingMachine.dao;

import VendingMachine.dto.Item;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The {@code VendingMachineDaoFileImpl} class is responsible for
 * interacting with Item objects in the VendingMachine
 */
public class VendingMachineDaoFileImpl implements VendingMachineDao {
    // Declare variables for ITEM_FILE name, FileDao object,
    // and a map to hold the Item objects
    private final String ITEM_FILE;
    private final FileDao fileDao;
    private final Map<String, Item> itemMap;

    /**
     * No-arg constructor sets ITEM_FILE to items.txt, initializes
     * the FileDao object, and initializes the map of Items
     * @throws PersistenceException if error occurs with ITEM_FILE
     */
    public VendingMachineDaoFileImpl() throws PersistenceException {
        ITEM_FILE = "items.txt";
        fileDao = new FileDaoImpl();
        itemMap = fileDao.readFile(ITEM_FILE);
    }

    /**
     * Constructor takes in String to set as the ITEM_FILE name,
     * initializes the FileDao object, and initializes the map of Items
     * @param vendingMachineTextFile name of .txt file with Items
     * @throws PersistenceException if error occurs with ITEM_FILE
     */
    public VendingMachineDaoFileImpl(String vendingMachineTextFile)
            throws PersistenceException{
        ITEM_FILE = vendingMachineTextFile;
        fileDao = new FileDaoImpl(ITEM_FILE);
        itemMap = fileDao.readFile(ITEM_FILE);
    }

    /**
     * Adds an Item object ot the item map, writes the Item
     * to the item file
     * @param itemName String name of Item
     * @param item Item object
     * @return Item object added to map
     * @throws PersistenceException if error occurs writing to file
     */
    @Override
    public Item addItem(String itemName, Item item) throws
            PersistenceException {
        Item result = itemMap.put(item.getItemName(), item);
        fileDao.writeFile(new ArrayList<>(itemMap.values()));
        return result;
    }

    /**
     * Returns list of all Item objects in the Item map
     * @return ArrayList of Item objects
     */
    @Override
    public List<Item> getAllItems() {
        return new ArrayList<>(itemMap.values());
    }

    /**
     * Returns an individual Item from the item map
     * @param name String name of Item
     * @return Item object
     */
    @Override
    public Item getItem(String name) {
        return itemMap.get(name);
    }

    /**
     * Removes an Item object ot the item map, writes to
     * the item file
     * @param item Item object
     * @return Item object removed from map
     * @throws PersistenceException if error occurs with file
     */
    @Override
    public Item removeItem(Item item) throws
            PersistenceException {
        Item result = itemMap.remove(item.getItemName());
        fileDao.writeFile(new ArrayList<>(itemMap.values()));
        return result;
    }

    /**
     * Changes the Item object's quantity, writes info to the item file
     * @param item Item object
     * @param newCount new item quantity
     * @throws PersistenceException if error occurs with file
     */
    @Override
    public Item changeInventoryQuantity(Item item, int newCount) throws
            PersistenceException {
        item.setItemQuantity(newCount);
        Item result = itemMap.replace(item.getItemName(), item);
        fileDao.writeFile(new ArrayList<>(itemMap.values()));
        return result;
    }
}
