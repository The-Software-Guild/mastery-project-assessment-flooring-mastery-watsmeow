package com.watsmeow.dao;

public interface DaoAuditInterface {

    public void writeAuditEntry(String entry) throws PersistenceException;

    public void writeOrderEntry(String entry) throws PersistenceException;

}
