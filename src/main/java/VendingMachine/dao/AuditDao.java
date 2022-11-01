package VendingMachine.dao;

public interface AuditDao {

    void writeAuditEntry(String entry) throws
            PersistenceException;
}
