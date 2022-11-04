package com.watsmeow.dao;

import com.watsmeow.dto.Order;
import com.watsmeow.dto.Product;
import com.watsmeow.dto.TaxInfo;

import java.util.List;
import java.util.Set;

public interface DaoInterface  {

    List<Product> getAllProducts() throws PersistenceException;

    List<Order> getAllOrders() throws PersistenceException;

    Product getProduct(String productType) throws PersistenceException;

    TaxInfo getTaxInfo(String state) throws PersistenceException;

    Set<Integer> getOrderNumbers();

    void createNewOrder(Order order) throws PersistenceException;

    List<TaxInfo> getAllTaxInfo() throws PersistenceException;

    void updateExistingOrder(Order order) throws PersistenceException;

    void deleteExistingOrder(Order order) throws PersistenceException;
}
