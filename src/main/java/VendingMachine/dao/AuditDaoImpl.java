package VendingMachine.dao;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

/**
 * The {@code AuditDaoImpl} class is responsible for writing
 * information to the audit .txt file
 */
public class AuditDaoImpl implements AuditDao {

    // Declare and initialize variable for name of audit log
    public static final String AUDIT_FILE = "audit.txt";

    /**
     * Writes information to audit dao .txt file when called
     * @param entry information to be written to file
     * @throws PersistenceException if error occurs when writing
     * to file
     */
    @Override
    public void writeAuditEntry(String entry) throws
            PersistenceException {
        // Declare PrintWriter object
        PrintWriter out;

        try {
            // Initialize PrintWriter object
            // Initialize FileWriter object with AUDIT_FILE name and
            // boolean true to append information to file
            out = new PrintWriter(new FileWriter(AUDIT_FILE, true));
        } catch (IOException e) {
            throw new PersistenceException
                    ("Could not persist audit information.", e);
        }

        // Declare and initialize timestamp of current date and time
        LocalDateTime timestamp = LocalDateTime.now();

        // Write the time stamp and audit log entry to the file
        out.println(timestamp + " : " + entry);

        // Force PrintWriter to write line to the file
        out.flush();
    }
}
