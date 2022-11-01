package VendingMachine.dao;

import VendingMachine.dto.Item;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * The {@code FileDaoImpl} class is responsible for unmarshalling
 * and marshalling Item object information, and reading/writing
 * Item object information from/to a .txt file
 */
public class FileDaoImpl implements FileDao {
    // Declare and initialize HashMap to store Items
    private static final Map<String, Item> itemMap = new HashMap<>();
    // Declare String to hold name of item file
    private final String ITEM_FILE;
    // Declare and initialize delimiter used in item file
    private static final String DELIMITER = "::";

    /**
     * No-args constructor, sets the item file name to items.txt
     */
    public FileDaoImpl() {
        ITEM_FILE = "items.txt";
    }

    /**
     * Constructor sets the item file name to the String it is passed
     * @param ITEM_FILE name of item file to be used for instance of
     *                  FileDaoImpl object
     */
    public FileDaoImpl(String ITEM_FILE) {
        this.ITEM_FILE = ITEM_FILE;
    }

    /**
     * Splits a String into tokens at the delimiter, creates
     * a new Item object with the information from the tokens
     * @param line Item represented as a String
     * @return Item object
     */
    @Override
    public Item unmarshallItem(String line) {
        // Split line from items.txt file into tokens,
        // store tokens in array
        String[] itemTokens = line.split(DELIMITER);

        // Retrieve item name from array element[0]
        String itemName = itemTokens[0];

        // Instantiate new Item object
        Item itemFromFile = new Item(itemName);

        // Set cost from array element[1]
        itemFromFile.setItemCost(new BigDecimal(itemTokens[1])
                .setScale(2, RoundingMode.DOWN));

        // Set item quantity from array element[2]
        itemFromFile.setItemQuantity(Integer.parseInt(itemTokens[2]));

        // Return Item object
        return itemFromFile;
    }

    /**
     * Converts an Item object into the appropriate String representation
     * with the Item's properties separated by the delimiter
     * @param item Item object
     * @return String representation of Item object
     */
    @Override
    public String marshallItem (Item item) {
        return item.getItemName() + DELIMITER + item.getItemCost() + DELIMITER
                + item.getItemQuantity();
    }

    /**
     * Reads a .txt file of Items and line by line unmarshalls each item
     * in the file, adds each item to a Map
     * @param fileName .txt file with information about all items
     * @return map of all items in file
     * @throws PersistenceException if error occurs reading file
     */
    @Override
    public Map<String, Item> readFile(String fileName)
            throws PersistenceException {
        // Declare Scanner object
        Scanner sc;

        try {
            // Initialize Scanner object
            sc = new Scanner(new BufferedReader(new FileReader(fileName)));
        } catch (FileNotFoundException e) {
            throw new PersistenceException("File not found.", e);
        }

        // Declare variable to store the most recent line read from the file
        String currentLine;
        // Declare variable to store the most recent item unmarshalled
        Item currentItem;

        // Go through each line of the file. Decode each line into an Item
        // object by calling the unmarshallItem method.
        // Process while file has more lines
        while (sc.hasNextLine()) {
            // Get the next line in the file
            currentLine = sc.nextLine();

            // Unmarshall the line into an Item object
            currentItem = unmarshallItem(currentLine);

            // Using the itemName as the map key, put the currentItem
            // into the item map
            itemMap.put(currentItem.getItemName(), currentItem);
        }
        // Close Scanner
        sc.close();

        // Return Map of Items
        return itemMap;
    }

    /**
     * Marshalls item objects and writes the String representations
     * of them to a .txt file
     * @param itemList List of all items
     * @throws PersistenceException if error occurs writing to file
     */
    @Override
    public void writeFile(List<Item> itemList) throws PersistenceException {
        // Declare PrintWriter Object
        PrintWriter out;

        try {
            // Initialize PrintWriter object
            // Initialize FileWriter object with ITEM_FILE name and
            // boolean true to append information to file
            out = new PrintWriter(new FileWriter(ITEM_FILE));

            // Initialize variable to store String representation of Item objects
            String itemAsText;

            // Marshall each Item object and write to file
            for (Item currentItem : itemList) {
                // Turn an Item object into a string
                itemAsText = marshallItem(currentItem);

                //Write the Item object to the file
                out.println(itemAsText);

                // Force PrintWriter to write line to the file
                out.flush();
            }
        } catch (IOException e) {
            throw new PersistenceException
                    ("Could not save item data", e);
        }

        // Close PrintWriter
        out.close();
    }
}
