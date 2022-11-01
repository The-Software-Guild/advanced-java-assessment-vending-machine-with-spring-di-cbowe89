package VendingMachine.service;

public class ItemInventoryException extends Exception {
    public ItemInventoryException(String msg) {
        super(msg);
    }

    public ItemInventoryException(String msg, Throwable cause) {
        super(msg,cause);
    }
}
