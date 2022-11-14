package com.watsmeow.dao;

import org.springframework.stereotype.Repository;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

@Repository
public class DaoAuditImpl implements DaoAuditInterface {

    // Defines the audit file
    public static final String AUDIT_FILE = "src/Data/audit.txt";

    // Method to write to the audit file when an order is saved, edited or deleted
    @Override
    public void writeAuditEntry(String entry) throws PersistenceException {
        PrintWriter out;
        try {
            /* Setting the second parameter to "true" opens the audit file in append mode ensures each entry will be
             * appended to the file rather than overwriting what was there before
             */
            out = new PrintWriter(new FileWriter(AUDIT_FILE, true));
        } catch (IOException e) {
            throw new PersistenceException("Could not persist audit information", e);
        }

        LocalDateTime timestamp = LocalDateTime.now();
        out.println(timestamp.toString() + " : " + entry);
        out.flush();
    }

}
