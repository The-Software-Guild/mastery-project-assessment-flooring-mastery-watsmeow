package com.watsmeow.service;

import com.watsmeow.dao.PersistenceException;
import com.watsmeow.dto.Order;
import com.watsmeow.dto.Product;
import com.watsmeow.dto.TaxInfo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ServiceInterface {

    List<Product> getAllProducts() throws ValidationException, PersistenceException;

    List<Order> getOrdersByDate(LocalDate date) throws PersistenceException;

    List<TaxInfo> getAllTaxInfo() throws PersistenceException;

    Order generateFullOrder(Order order) throws ValidationException, PersistenceException;

    Optional<Order> getOrderToEditOrDeleteOrder(LocalDate date, int orderNumber) throws PersistenceException;

    void editOrder(Order order) throws PersistenceException;

    void deleteOrder(Order order) throws PersistenceException;

    void saveOrder(Order order) throws PersistenceException;
}
