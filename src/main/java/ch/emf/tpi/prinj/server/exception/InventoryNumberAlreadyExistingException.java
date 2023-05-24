package ch.emf.tpi.prinj.server.exception;

public class InventoryNumberAlreadyExistingException extends RuntimeException{
    public InventoryNumberAlreadyExistingException(String inventoryNumber) {
        super("Inventory number : " + inventoryNumber + " already existing");
    }
}
