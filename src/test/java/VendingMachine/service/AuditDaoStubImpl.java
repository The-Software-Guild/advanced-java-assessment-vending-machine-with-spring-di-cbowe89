package VendingMachine.service;

import VendingMachine.dao.AuditDao;
import VendingMachine.dao.PersistenceException;

public class AuditDaoStubImpl implements AuditDao {
    @Override
    public void writeAuditEntry(String entry) throws
            PersistenceException {
        // Do nothing...
    }
}
