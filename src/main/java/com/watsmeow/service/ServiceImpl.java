package com.watsmeow.service;

import com.watsmeow.dao.DaoAuditInterface;
import com.watsmeow.dao.DaoInterface;
import com.watsmeow.dao.PersistenceException;
import com.watsmeow.dto.Order;
import com.watsmeow.dto.Product;
import com.watsmeow.dto.TaxInfo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

public class ServiceImpl implements ServiceInterface {

    private DaoInterface dao;

    private DaoAuditInterface auditDao;

    public ServiceImpl (DaoInterface dao, DaoAuditInterface auditDao) {
        this.dao = dao;
        this.auditDao = auditDao;
    }

    public List<Product> getAllProducts() throws PersistenceException {
        return dao.getAllProducts();
    }

    public List<TaxInfo> getAllTaxInfo() throws PersistenceException {
        return dao.getAllTaxInfo();
    }

    public List<Order> getOrdersByDate(LocalDate date) throws PersistenceException {
        return dao.getAllOrders()
            .stream()
            .filter(order -> order.getOrderDate().equals(date))
            .collect(Collectors.toList());
    }

    public Product getProduct(Order order) throws PersistenceException {
        return dao.getProduct(order.getProductType());
    }


    public Order generateFullOrder(Order order) throws PersistenceException {
        Product product = dao.getProduct(order.getProductType());
        TaxInfo taxInfo = dao.getTaxInfo(order.getState());
        order.setTaxRate(taxInfo.getTaxRate());
        order.setProductType(product.getProductType());
        order.setCostPerSqFt(product.getCostPerSqFt());
        BigDecimal materialCost = order.getArea().multiply(product.getCostPerSqFt()).setScale(2);
        BigDecimal laborCost = order.getArea().multiply(product.getLaborCostPerSqFt()).setScale(2);
        BigDecimal tax = materialCost.add(laborCost).multiply(taxInfo.getTaxRate()
                .divide(new BigDecimal(100))).setScale(2, RoundingMode.HALF_UP);
        BigDecimal total = materialCost.add(laborCost.add(tax).setScale(2));
        order.setLaborCostPerSqFt(product.getLaborCostPerSqFt());
        order.setMaterialCost(materialCost);
        order.setLaborCost(laborCost);
        order.setTax(tax);
        order.setTotal(total);
        return order;
    }

    public Order getOrderToEditOrder(LocalDate date, int orderNumber) throws PersistenceException {
        List<Order> orderList = getOrdersByDate(date);
        Order singleOrder = orderList.stream()
                .filter(order -> order.getOrderNumber() == orderNumber)
                .findFirst()
                .get();
        return singleOrder;
    }

    public void saveOrder(Order order) throws PersistenceException {
        int orderNumber = dao.getOrderNumbers()
                .stream()
                .max(Integer::compare).get() + 1;
        order.setOrderNumber(orderNumber);
        dao.createNewOrder(order);
    }

    public void editOrder(Order order) throws PersistenceException {
        dao.updateExistingOrder(order);
    }

}
