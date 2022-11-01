package VendingMachine.controller;

import VendingMachine.dao.PersistenceException;
import VendingMachine.dto.Change;
import VendingMachine.dto.Coins;
import VendingMachine.dto.Item;
import VendingMachine.service.InsufficientFundsException;
import VendingMachine.service.ItemInventoryException;
import VendingMachine.service.ServiceLayer;
import VendingMachine.service.ServiceLayerImpl;
import VendingMachine.ui.UserIO;
import VendingMachine.ui.UserIOConsoleImpl;
import VendingMachine.ui.View;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 * The {@code VendingMachineController} class orchestrates the actions
 * of the other components in the application to accomplish the
 * application's goals.
 * It controls the flow of the application, allows for navigation via
 * the menu, and provides methods to interact with the VendingMachine.
 */
public class Controller {

    // Declare VendingMachineView and VendingMachineServiceLayer objects
    private final View view;
    private final ServiceLayer serviceLayer;

    /**
     * Controller constructor: initializes View and ServiceLayer
     * object by creating new instances of the objects.
     * @throws PersistenceException if error occurs writing file
     */
    public Controller() throws PersistenceException {
        UserIO io = new UserIOConsoleImpl();
        view = new View(io);
        serviceLayer = new ServiceLayerImpl();
    }

    /**
     * Constructor initializes the view and dao objects
     * via dependency injection.
     * @param view VendingMachineView object
     * @param serviceLayer VendingMachineService object
     */
    public Controller(View view, ServiceLayer serviceLayer) {
        // Initialize View and Service
        this.view = view;
        this.serviceLayer = serviceLayer;
    }

    /**
     * Method controls the application
     * @throws PersistenceException if error occurs with file
     */
    public void run() throws PersistenceException {
        // Declare and initialize beginning balance of $0.00
        BigDecimal balance = new BigDecimal(0);

        // Declare and initialize variables
        boolean runApplication = true;
        int startMenuSelection, mainMenuSelection;
        // List of all items with qty > 0
        List<Item> itemList = serviceLayer.getAllItems();

        // Display welcome banner and item list
        view.displayWelcomeBanner();
        view.displayAllItems(itemList);

        // Determine if user wants to use vending machine
        startMenuSelection = view.getStartMenuSelection();
        switch (startMenuSelection) {
            case 1 -> view.displayAddFundsReminder();
            case 2 -> runApplication = false;
            default -> view.displayUnknownCommand();
        }

        // Run Vending Machine or Exit
        try {
            // While runApplication is true
            while (runApplication) {
                // Display the menu, update the menu selection from user input
                mainMenuSelection = view.getMainMenuSelection();

                switch (mainMenuSelection) {
                    case 1 ->  // Add funds to balance
                            balance = addFunds(balance);
                    case 2 -> // Buy Items
                        {
                            try {
                                balance = purchaseItems(balance, itemList);
                            } catch (ItemInventoryException |
                                     InsufficientFundsException e) {
                                view.displayBalance(balance);
                                view.displayErrorMessage(e.getMessage());
                            }
                        }
                    case 3 -> { // Quit VendingMachine
                        try {
                            // Display end balance/change due and exit
                            quit(balance);
                        } catch (InsufficientFundsException e) {
                            view.displayBalance(balance);
                            view.displayErrorMessage(e.getMessage());
                        }
                        runApplication = false;
                    }
                    default -> // Menu selection is not valid
                            view.displayUnknownCommand();
                }
            }
            view.displayExitMessage();
        } catch (PersistenceException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    /**
     * Method for user to add funds to VendingMachine
     * @param balance Passes current balance
     * @return balance after funds are added
     */
    private BigDecimal addFunds(BigDecimal balance) {
        return view.addFundsDisplay(balance);
    }

    /**
     * Method for user to purchase items
     * @param balance current balance
     * @param itemList list of all in-stock items
     * @return balance after purchase
     * @throws PersistenceException if error occurs writing file
     * @throws ItemInventoryException if item quantity is invalid
     * @throws InsufficientFundsException if balance is insufficient
     */
    private BigDecimal purchaseItems(BigDecimal balance, List<Item> itemList)
            throws PersistenceException,
            ItemInventoryException,
            InsufficientFundsException {
        // Declare and initialize variables
        int itemSelection;

        // if balance is $0.00, user must add funds first
        if (balance.compareTo(new BigDecimal(0)) < 1) {
            throw new InsufficientFundsException("Add money to make a purchase!");
        }
        else { // Allow user to purchase item
            // Display purchaseItem banner
            view.purchaseItemBanner();

            // Re-display items for ease of use
            view.displayAllItems(itemList);

            // Get itemSelection, store Item object as purchasedItem
            itemSelection = view.getItemSelection(itemList.size());
            Item purchasedItem = itemList.get(itemSelection - 1);

            // Sell Item - updates item quantity in file, changes user balance
            balance = serviceLayer.sellItem(balance, purchasedItem);

            // Display successful purchase banner
            view.purchaseSuccessBanner();
            // Display remaining balance
            view.displayPurchase(purchasedItem, balance);

            // Calculate change from remaining balance, display
            // coins to be dispensed as change
            HashMap<Coins, Integer> changeMap = Change.getChange(balance);
            view.printChange(changeMap);
        }

        // Balance set to zero do to remainder dispensed as change
        return balance = new BigDecimal(0);
    }

    /**
     * Method to dispense change if balance is > 0 when user wants
     * to quit the VendingMachine
     * @param balance current user balance
     * @throws InsufficientFundsException if balance is insufficient
     */
    private void quit(BigDecimal balance)
            throws InsufficientFundsException {
        // Dispense change before exiting if balance is greater than $0.00
        if (balance.compareTo(new BigDecimal(0)) > 0) {
            HashMap<Coins, Integer> changeMap = Change.getChange(balance);
            view.printChange(changeMap);
        }
    }
}
