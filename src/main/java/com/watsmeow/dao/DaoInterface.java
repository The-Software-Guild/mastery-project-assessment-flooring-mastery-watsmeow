package com.watsmeow.dao;

import com.watsmeow.dto.Order;
import com.watsmeow.dto.Product;
import com.watsmeow.dto.TaxInfo;

import java.util.List;
import java.util.Set;

public interface DaoInterface  {

    /**
     * "Get All" methods:
     * Retrieves lists of objects
     */
    List<Product> getAllProducts() throws PersistenceException;

    List<Order> getAllOrders() throws PersistenceException;

    /**
     * "Get" singular methods:
     * Retrieves an object based on an abject value
     */
    Product getProduct(String productType) throws PersistenceException;

    TaxInfo getTaxInfo(String state) throws PersistenceException;

    /**
     * Method to retrieve a set of all the order numbers in the system
     */
    Set<Integer> getOrderNumbers();

    /**
     * Method to create a new order
     * @param order is the order being added
     */
    void createNewOrder(Order order) throws PersistenceException;

    List<TaxInfo> getAllTaxInfo() throws PersistenceException;

    void updateExistingOrder(Order order) throws PersistenceException;

    /**
     * Method to delete an order from the system
     * @param order is the order object being deleted
     */
    void deleteExistingOrder(Order order) throws PersistenceException;
}
