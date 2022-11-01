package VendingMachine.ui;

import VendingMachine.dto.Coins;
import VendingMachine.dto.Item;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;

/**
 * The {@code View} class is responsible for all information,
 * banners, etc. displayed to the user throughout the application
 */
public class View {
    // Declare UserIO object
    private final UserIO io;

    /**
     * Constructor for View object
     * @param io UserIO object
     */
    public View(UserIO io) {
        this.io = io;
    }

    /**
     * Prompts user to see if they want to use the
     * VendingMachine application or quit
     * @return user selection (int)
     */
    public int getStartMenuSelection() {
        return io.readInt("Enter 1 to CONTINUE or 2 to QUIT.",
                1, 2);
    }

    /**
     * Prints the mainMenuBanner and the VendingMachine's main menu
     * @return user selection (int)
     */
    public int getMainMenuSelection() {
        mainMenuBanner();
        io.print("1. Add Money");
        io.print("2. Purchase Item");
        io.print("3. Quit");

        return io.readInt("Please select an operation.", 1, 3);
    }

    /**
     * Displays the addFundsBanner and current balance, prompts
     * the user for the amount of money they want to add to their
     * current balance, then displays the fundsAddedMessage and
     * new balance
     * @param balance current balance
     * @return new balance after funds are added
     */
    public BigDecimal addFundsDisplay(BigDecimal balance) {
        addFundsBanner();
        displayBalance(balance);
        balance = balance.add(io.readBigDecimal(
                "Enter funds to add ($0.01 - $100.00): ",
                new BigDecimal("0.01"), new BigDecimal("100.00")));
        fundsAddedMessage();
        displayBalance(balance);
        io.readString("Please hit enter to continue.");
        return balance;
    }

    /**
     * Displays the current balance
     * @param balance current balance
     */
    public void displayBalance(BigDecimal balance) {
        io.print("Current balance: $" +
                balance.setScale(2, RoundingMode.HALF_UP));
    }

    /**
     * Prompts the  user for item number that they wish to purchase
     * @param itemListSize item selected must be between 1 and
     *                     the itemListSize
     * @return number for item selection (int)
     */
    public int getItemSelection(int itemListSize) {
        return io.readInt("Please select an item",
                1, itemListSize);
    }

    /**
     * Displays the displayAllItemsBanner,then a numbered
     * list of all available items
     * @param itemList list of available items
     */
    public void displayAllItems(List<Item> itemList) {
        displayAllItemsBanner();

        // Number to be displayed by items
        int itemNum = 1;

        for (Item i : itemList) {
            // Print formatted number, itemName, and itemCost
            io.printf(itemNum, i.getItemName(), i.getItemCost());
            // Increment itemNum for next item to be printed
            itemNum++;
        }

        // Print blank line after list for readability
        io.print("");
    }

    /**
     * Displays the name and cost of the purchased item and
     * the remaining balance after the purchase
     * @param item purchased item
     * @param balance remaining balance after purchase
     */
    public void displayPurchase(Item item, BigDecimal balance) {
        io.print("Purchased: " + item.getItemName() +
                "\nCost: $" + item.getItemCost() +
                "\nRemaining Balance: $" + balance);
    }

    /**
     * Prints the types of coins and number of each type
     * to be dispensed to the user as change
     * @param map coin types/amounts to be returned to user for change
     */
    public void printChange(HashMap<Coins, Integer> map) {
        io.print("\nDispensing Change:");
        for(Coins c : map.keySet()) {
            io.print("\t"+ c.name() + "($" + c.getValue() + "): " + map.get(c));
        }
    }

    /**
     * Displays the Welcome Banner
     */
    public void displayWelcomeBanner() {
        io.print("\n-------------------------------");
        io.print("Welcome to the Vending Machine!");
        io.print("-------------------------------");
    }

    /**
     * Displays reminder for user to add funds before
     * an item can be purchased
     */
    public void displayAddFundsReminder() {
        io.print("\n*** Remember to add funds before selecting an item. ***");
    }

    /**
     * Displays banner for item list
     */
    public void displayAllItemsBanner() {
        io.print("\n===== Vending Machine Items =====");
    }

    /**
     * Displays main menu banner
     */
    public void mainMenuBanner() {
        io.print("\n===== MAIN MENU =====");
    }

    /**
     * Displays add funds banner
     */
    public void addFundsBanner() {
        io.print("\n===== ADD FUNDS =====");
    }

    /**
     * Displays message to thank the user for adding money
     */
    public void fundsAddedMessage() {
        io.print("Money added! Thank you.");
    }


    /**
     * Displays the purchase item banner
     */
    public void purchaseItemBanner() {
        io.print("\n===== PURCHASE ITEM =====");
    }

    /**
     * Displays banner for successful purchase
     */
    public void purchaseSuccessBanner() {
        io.print("Item successfully purchased!");
    }

    /**
     * Displays exit message
     */
    public void displayExitMessage() {
        io.print("Exiting Vending Machine... Goodbye!!!");
    }

    /**
     * Displays unknown command message, prompts user to
     * press enter to continue.
     */
    public void displayUnknownCommand() {
        io.print("\n===== UNKNOWN COMMAND =====");
        io.readString("Please hit enter to continue.");
    }

    /**
     * Displays error message
     * @param errorMsg error message
     */
    public void displayErrorMessage(String errorMsg) {
        io.print("\n===== ERROR =====");
        io.print(errorMsg);
        io.readString("Please hit enter to continue.");
    }
}
