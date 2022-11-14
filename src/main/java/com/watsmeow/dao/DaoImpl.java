package com.watsmeow.dao;

import com.watsmeow.dto.Order;
import com.watsmeow.dto.Product;
import com.watsmeow.dto.TaxInfo;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class DaoImpl implements DaoInterface {

    DaoFileInterface fio;

    // Declaring hashmaps to store information from files
    private Map<String, Product> products = new HashMap();

    private Map<String, TaxInfo> taxinfo = new HashMap();

    private Map<Integer, Order> orders = new HashMap();

    // Declaring files to in order to get file info
    private static final String PRODUCTS_FILE = "src/Data/products.txt";

    private static final String TAXES_FILE = "src/Data/taxinfo.txt";

    private static final String ORDERS_FILE = "src/Orders";

    public DaoImpl() throws PersistenceException {
        fio = new DaoFileImpl();
        products = fio.loadProducts(PRODUCTS_FILE);
        taxinfo = fio.loadTaxInfo(TAXES_FILE);
        orders = fio.loadOrder(ORDERS_FILE);
    }

    // Gets all products and returns list
    @Override
    public List<Product> getAllProducts() throws PersistenceException {
        products = fio.loadProducts(PRODUCTS_FILE);
        return new ArrayList<>(products.values());
    }

    // Gets all tax info and returns list
    public List<TaxInfo> getAllTaxInfo() throws PersistenceException {
        taxinfo = fio.loadTaxInfo(TAXES_FILE);
        return new ArrayList<>(taxinfo.values());
    }

    // Gets all orders and returns list
    @Override
    public List<Order> getAllOrders() throws PersistenceException {
        orders = fio.loadOrder(ORDERS_FILE);
        return new ArrayList<>(orders.values());
    }

    // Gets and returns a single product based on the product type
    @Override
    public Product getProduct(String productType) throws PersistenceException {
        products = fio.loadProducts(PRODUCTS_FILE);
        return products.get(productType.toLowerCase());
    }

    // Gets and returns tax info based on the state name
    @Override
    public TaxInfo getTaxInfo(String state) throws PersistenceException {
        taxinfo = fio.loadTaxInfo(TAXES_FILE);
        return taxinfo.get(state.toLowerCase());
    }

    // Gets order numbers a returns them in a set
    public Set<Integer> getOrderNumbers() {
        return orders.keySet();
    }

    // Puts a new order into the hashmap and writes to file
    public void createNewOrder(Order order) throws PersistenceException {
        orders.put(order.getOrderNumber(), order);
        fio.writeOrder(order);
    }

    // Updates an existing order in the hashmap and writes to file
    public void updateExistingOrder(Order order) throws PersistenceException {
        orders.put(order.getOrderNumber(), order);
        fio.writeAllOrders(orders.values()
                .stream()
                .filter(order1 -> order1.getOrderDate().equals(order.getOrderDate()))
                .collect(Collectors.toList()));
    }

    // Deletes an order from hashmap and rewrites the file
    @Override
    public void deleteExistingOrder(Order order) throws PersistenceException {
        orders.remove(order.getOrderNumber());
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

    // Writes all order info to the backup folder
    @Override
    public void exportAllData() throws PersistenceException {
        fio.writeToExportAllData();
    }
}
