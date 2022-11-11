package com.watsmeow.dao;

import com.watsmeow.dto.Order;
import com.watsmeow.dto.Product;
import com.watsmeow.dto.TaxInfo;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class DaoImpl implements DaoInterface {

    DaoFileInterface fio;

    private Map<String, Product> products = new HashMap();

    private Map<String, TaxInfo> taxinfo = new HashMap();

    private Map<Integer, Order> orders = new HashMap();

    private static final String PRODUCTS_FILE = "src/Data/products.txt";

    private static final String TAXES_FILE = "src/Data/taxinfo.txt";

    private static final String ORDERS_FILE = "src/Orders";

    public DaoImpl() throws PersistenceException {
        fio = new DaoFileImpl();
        products = fio.loadProducts(PRODUCTS_FILE);
        taxinfo = fio.loadTaxInfo(TAXES_FILE);
        orders = fio.loadOrder(ORDERS_FILE);
    }

    @Override
    public List<Product> getAllProducts() throws PersistenceException {
        products = fio.loadProducts(PRODUCTS_FILE);
        return new ArrayList<>(products.values());
    }

    public List<TaxInfo> getAllTaxInfo() throws PersistenceException {
        taxinfo = fio.loadTaxInfo(TAXES_FILE);
        return new ArrayList<>(taxinfo.values());
    }

    @Override
    public List<Order> getAllOrders() throws PersistenceException {
        orders = fio.loadOrder(ORDERS_FILE);
        return new ArrayList<>(orders.values());
    }

    @Override
    public Product getProduct(String productType) throws PersistenceException {
        products = fio.loadProducts(PRODUCTS_FILE);
        return products.get(productType.toLowerCase());
    }

    @Override
    public TaxInfo getTaxInfo(String stateAbbrev) throws PersistenceException {
        taxinfo = fio.loadTaxInfo(TAXES_FILE);
        return taxinfo.get(stateAbbrev.toLowerCase());
    }

    public Set<Integer> getOrderNumbers() {
        return orders.keySet();
    }

    public void createNewOrder(Order order) throws PersistenceException {
        orders.put(order.getOrderNumber(), order);
        fio.writeOrder(order);
    }

    public void updateExistingOrder(Order order) throws PersistenceException {
        orders.put(order.getOrderNumber(), order);
        fio.writeAllOrders(orders.values()
                .stream()
                .filter(order1 -> order1.getOrderDate().equals(order.getOrderDate()))
                .collect(Collectors.toList()));
    }

    @Override
    public void deleteExistingOrder(Order order) throws PersistenceException {
        orders.remove(order.getOrderNumber(), order);
            if (orders.values()
                    .stream()
                    .filter(order2 -> order2.getOrderDate().equals(order.getOrderDate()))
                    .collect(Collectors.toList()).size() > 0) {
                fio.writeAllOrders(orders.values()
                        .stream()
                        .filter(order1 -> order1.getOrderDate().equals(order.getOrderDate()))
                        .collect(Collectors.toList()));
            } else {
                fio.deleteOrderFile(order.getOrderDate());
            }
    }
}
