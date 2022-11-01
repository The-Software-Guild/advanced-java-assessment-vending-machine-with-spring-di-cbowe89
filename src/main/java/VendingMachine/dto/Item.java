package VendingMachine.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * The {@code Items} class is responsible for creating
 * and updating Item objects
 */
public class Item {
    // Declare variables for Item objects
    private String itemName;
    private BigDecimal itemCost;
    private int itemQuantity;

    /**
     * Constructor takes one parameter (itemName) and
     * creates an Item object
     * @param itemName name of Item
     */
    public Item(String itemName) {
        this.itemName = itemName;
    }

    /**
     * Constructor takes 3 parameters and creates an Item object
     * @param itemName item name
     * @param itemCost item cost
     * @param itemQuantity item quantity
     */
    public Item(String itemName, BigDecimal itemCost, int itemQuantity) {
        this.itemName = itemName;
        this.itemCost = itemCost;
        this.itemQuantity = itemQuantity;
    }

    /**
     * Gets the name of an Item object
     * @return itemName
     */
    public String getItemName() {
        return itemName;
    }

    /**
     * Sets the name of an Item object
     * @param itemName name of Item to update
     */
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    /**
     * Gets the cost of an Item object
     * @return itemCost
     */
    public BigDecimal getItemCost() {
        return itemCost;
    }

    /**
     * Sets the cost of an Item object
     * @param cost Cost of item
     */
    public void setItemCost(BigDecimal cost) {
        this.itemCost = cost;
    }

    /**
     * Gets the quantity of an Item object
     * @return itemQuantity
     */
    public int getItemQuantity() {
        return itemQuantity;
    }

    /**
     * Sets the quantity of an Item object
     * @param itemQuantity Quantity of item
     */
    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Item item = (Item) o;
        return itemQuantity == item.itemQuantity
                && Objects.equals(itemName, item.itemName)
                && Objects.equals(itemCost, item.itemCost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemName, itemCost, itemQuantity);
    }

    @Override
    public String toString() {
        return "Item{" + "name = " + itemName + ", cost = " + itemCost
                + ", itemQuantity = " + itemQuantity + '}';
    }
}
