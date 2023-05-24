package ch.emf.tpi.prinj.server.exception;

public class EquipmentNotFoundException extends RuntimeException {
    public EquipmentNotFoundException(Long id) {
        super("Could not find equipment with id : "+ id);
    }
}
