package com.watsmeow.dao;

import com.watsmeow.dto.Order;
import com.watsmeow.dto.Product;
import com.watsmeow.dto.TaxInfo;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface DaoFileInterface {

    Product unmarshalProduct(String line);

    TaxInfo unmarshalTaxInfo(String line);

    Order unmarshalOrder(LocalDate localDate, String line);

    Map<String, Product> loadProducts(String file) throws PersistenceException;

    Map<String, TaxInfo> loadTaxInfo(String file) throws PersistenceException;

    Map<Integer, Order> loadOrder(String file) throws PersistenceException;
    String marshalProduct(Product product);

    String marshalTaxInfo(TaxInfo taxInfo);

    String marshalOrder(Order order);

    void writeOrder(Order order) throws PersistenceException;

    void writeAllOrders(List<Order> orderList) throws PersistenceException;

    void deleteOrderFile(LocalDate orderDate) throws PersistenceException;
}