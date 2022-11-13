package com.watsmeow.service;

import com.watsmeow.dao.PersistenceException;
import com.watsmeow.dto.Order;
import com.watsmeow.dto.Product;
import com.watsmeow.dto.TaxInfo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ServiceInterface {

    /**
     * "Get All" methods:
     * Retrieves lists of objects by utilizing the dao
     */
    List<Product> getAllProducts() throws ValidationException, PersistenceException;

    List<TaxInfo> getAllTaxInfo() throws PersistenceException;

    /**
     * Retrieves lists of objects utilizing the dao, then returns a list filtered by a lambda to only include
     * orders of the user specified date
     */
    List<Order> getOrdersByDate(LocalDate date) throws PersistenceException;

    /**
     * Takes in the user information for a new order and generates a full order by calculating and setting fields
     * requiring business logic
     */
    Order generateFullOrder(Order order) throws ValidationException, PersistenceException;

    /**
     * Retrieves an order based on the user supplied order number and order date and returns the full order object
     */
    Optional<Order> getOrderToEditOrDeleteOrder(LocalDate date, int orderNumber) throws PersistenceException;

    /**
     * Edits an existing order
     */
    void editOrder(Order order) throws PersistenceException;

    /**
     * Deletes an existing order
     */
    void deleteOrder(Order order) throws PersistenceException;

    /**
     * Saves a new order or saves an updated order.
     * If new order, generates and assigns a unique order number that is +1 > the largest existing order number
     */
    void saveOrder(Order order) throws PersistenceException;

    void exportData() throws PersistenceException;
}
