package VendingMachine.service;

import VendingMachine.dao.PersistenceException;
import VendingMachine.dao.VendingMachineDao;
import VendingMachine.dto.Item;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class DaoStubImpl implements VendingMachineDao {
    public Item onlyItem;

    public DaoStubImpl() {
        onlyItem = new Item("Doritos",
                new BigDecimal("1.23"), 9);
    }

    public DaoStubImpl(Item testItem) {
        this.onlyItem= testItem;
    }

    @Override
    public Item addItem(String itemName, Item item)
            throws PersistenceException {
        if (itemName.equals(onlyItem.getItemName()))
            return onlyItem;
        else
            return null;
    }

    @Override
    public List<Item> getAllItems() throws PersistenceException {
        List<Item> itemList = new ArrayList<>();
        itemList.add(onlyItem);
        return itemList;
    }

    @Override
    public Item getItem(String itemName) throws PersistenceException {
        if (itemName.equals(onlyItem.getItemName()))
            return onlyItem;
        else
            return null;
    }

    @Override
    public Item removeItem(Item item) throws PersistenceException {
        if (item.getItemName().equals(onlyItem.getItemName()))
            return onlyItem;
        else
            return null;
    }

    @Override
    public Item changeInventoryQuantity(Item item, int newQuantity)
            throws PersistenceException {
        if (item.getItemQuantity() + newQuantity >= 0) {
            onlyItem.setItemQuantity(newQuantity);
            return onlyItem;
        }
        else
            return null;
    }
}
