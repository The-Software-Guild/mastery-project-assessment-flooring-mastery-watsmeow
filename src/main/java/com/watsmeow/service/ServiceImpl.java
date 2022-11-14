package com.watsmeow.service;

import com.watsmeow.dao.DaoAuditInterface;
import com.watsmeow.dao.DaoInterface;
import com.watsmeow.dao.PersistenceException;
import com.watsmeow.dto.Order;
import com.watsmeow.dto.Product;
import com.watsmeow.dto.TaxInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServiceImpl implements ServiceInterface {

    // Bring in classes to construct the service layer
    private DaoInterface dao;

    private DaoAuditInterface auditDao;

    // Construct the service implementation
    @Autowired
    public ServiceImpl (DaoInterface dao, DaoAuditInterface auditDao) {
        this.dao = dao;
        this.auditDao = auditDao;
    }

    // Method to list all products, calls the dao
    public List<Product> getAllProducts() throws PersistenceException {
        return dao.getAllProducts();
    }

    // Method to list orders by order date, uses the dao getAllOrders method and then filters for date
    public List<Order> getOrdersByDate(LocalDate date) throws PersistenceException {
        return dao.getAllOrders()
                .stream()
                .filter(order -> order.getOrderDate().equals(date))
                .collect(Collectors.toList());
    }

    // Method to get all tax information, calls the dao
    public List<TaxInfo> getAllTaxInfo() throws PersistenceException {
        return dao.getAllTaxInfo();
    }

    // Method to generate a full order using the input provided by the user
    public Order generateFullOrder(Order order) throws PersistenceException {
        Product product = dao.getProduct(order.getProductType());
        TaxInfo taxInfo = dao.getTaxInfo(order.getState());
        order.setStateAbbrev(taxInfo.getStateAbbrev());
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

    // Method to get an order by order number in order to delete it from memory
    public Optional<Order> getOrderToEditOrDeleteOrder(LocalDate date, int orderNumber) throws PersistenceException {
        List<Order> orderList = getOrdersByDate(date);
        Optional<Order> singleOrder = orderList.stream()
                .filter(order -> order.getOrderNumber() == orderNumber)
                .findFirst();
        return singleOrder;
    }

    // Method to edit an order and write an entry to the audit log
    public void editOrder(Order order) throws PersistenceException {
        dao.updateExistingOrder(order);
        auditDao.writeAuditEntry("Order " + order.getOrderNumber() + " edited");
    }

    // Method to delete an order and write an entry to the audit log
    public void deleteOrder(Order order) throws PersistenceException {
        dao.deleteExistingOrder(order);
        auditDao.writeAuditEntry("Order " + order.getOrderNumber() + " deleted");
    }

    // Method to save an order and write an entry to the audit log
    public void saveOrder(Order order) throws PersistenceException {
        int orderNumber = dao.getOrderNumbers()
                .stream()
                .max(Integer::compare).get() + 1;
        order.setOrderNumber(orderNumber);
        dao.createNewOrder(order);
        auditDao.writeAuditEntry("Order " + order.getOrderNumber() + " saved");
    }

    // Method to export all data to the backup folder
    public void exportData() throws PersistenceException {
        dao.exportAllData();
    }
}
