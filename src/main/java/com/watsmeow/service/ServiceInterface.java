package com.watsmeow.service;

import com.watsmeow.dao.PersistenceException;
import com.watsmeow.dto.Order;
import com.watsmeow.dto.Product;
import com.watsmeow.dto.TaxInfo;

import java.time.LocalDate;
import java.util.List;

public interface ServiceInterface {

    List<Product> getAllProducts() throws ValidationException, PersistenceException;

    Order generateFullOrder(Order order) throws ValidationException, PersistenceException;

    void saveOrder(Order order) throws PersistenceException;

    List<Order> getOrdersByDate(LocalDate date) throws PersistenceException;

    List<TaxInfo> getAllTaxInfo() throws PersistenceException;
}
