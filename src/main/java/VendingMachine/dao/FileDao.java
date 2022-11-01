package VendingMachine.dao;

import VendingMachine.dto.Item;

import java.util.List;
import java.util.Map;

public interface FileDao {
    Item unmarshallItem(String line);

    String marshallItem(Item item);

    Map<String, Item> readFile(String fileName) throws
            PersistenceException;

    void writeFile(List<Item> itemList) throws
            PersistenceException;
}
