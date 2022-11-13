package com.watsmeow.dao;

import com.watsmeow.dto.Order;
import com.watsmeow.dto.Product;
import com.watsmeow.dto.TaxInfo;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface DaoFileInterface {

    /**
     * Unmarshal methods:
     * Retrieves information from the items.txt file
     * @param line is the item being retrieved as stored as a string in the .txt file
     * @return the selected item object
     */
    Product unmarshalProduct(String line);

    TaxInfo unmarshalTaxInfo(String line);

    Order unmarshalOrder(LocalDate localDate, String line);

    /**
     * Load methods:
     * Loads all the items from the .txt file into their respective hashmaps
     * @param file is the file in string format that's being written to
     * @return is a hashmap of objects
     */
    Map<String, Product> loadProducts(String file) throws PersistenceException;

    Map<String, TaxInfo> loadTaxInfo(String file) throws PersistenceException;

    Map<Integer, Order> loadOrder(String file) throws PersistenceException;

    /**
     * Marshal methods:
     * Marshals the item hashmap into strings for storage in the .txt file and later retrieval
     * @params are the object being stored in the file
     * @return the selected item object as a string with fields delimited by ,
     */
    String marshalProduct(Product product);

    String marshalTaxInfo(TaxInfo taxInfo);

    String marshalOrder(Order order);

    /**
     * Write methods:
     * Writes all items in local memory to the .txt file based on the load____ file format
     * Throws an exception if it cannot write the items to the file
     * @params are either a singular order if you are writing a new file, or a list of orders if you are
     * adding to and therefore re-writing an existing file
     */
    void writeOrder(Order order) throws PersistenceException;

    void writeAllOrders(List<Order> orderList) throws PersistenceException;

    /**
     * Delete method:
     * Deletes a .txt file if it is empty of orders
     */
    void deleteOrderFile(LocalDate orderDate) throws PersistenceException;

    void writeToExportAllData() throws PersistenceException;
}