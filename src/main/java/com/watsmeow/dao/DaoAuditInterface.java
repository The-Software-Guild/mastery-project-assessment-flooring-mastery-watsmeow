package com.watsmeow.dao;

public interface DaoAuditInterface {

    /**
     * Method to write an entry to the log file. Throws an exception because there is the potential for it to
     * run into problems when writing to the audit file.
     */
    public void writeAuditEntry(String entry) throws PersistenceException;

}
