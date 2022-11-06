package com.watsmeow.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Order {

    private LocalDate orderDate;

    private int orderNumber;

    private String customerName;

    private String state;


    private String stateAbbrev;

    private BigDecimal taxRate;

    private String productType;

    private BigDecimal area;

    private BigDecimal costPerSqFt;

    private BigDecimal laborCostPerSqFt;

    private BigDecimal materialCost;

    private BigDecimal laborCost;

    private BigDecimal tax;

    private BigDecimal total;

    public Order(String customerName, String state, String productType, BigDecimal area) {
        this.customerName = customerName;
        this.state = state;
        this.productType = productType;
        this.area = area;
    }


    // Setter methods
    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }


    public void setState(String state) {
        this.state = state;
    }

    
    public void setStateAbbrev(String stateAbbrev) {
        this.stateAbbrev = stateAbbrev;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public void setCostPerSqFt(BigDecimal costPerSqFt) {
        this.costPerSqFt = costPerSqFt;
    }

    public void setLaborCostPerSqFt(BigDecimal laborCostPerSqFt) {
        this.laborCostPerSqFt = laborCostPerSqFt;
    }

    public void setMaterialCost(BigDecimal materialCost) {
        this.materialCost = materialCost;
    }

    public void setLaborCost(BigDecimal laborCost) {
        this.laborCost = laborCost;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }


    // Getter methods
    public LocalDate getOrderDate() {
        return orderDate;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public String getCustomerName() {
        return customerName;
    }


    public String getState() {
        return state;
    }

    public String getStateAbbrev() {
        return stateAbbrev;
    }

    public BigDecimal getArea() {
        return area;
    }

    public String getProductType() {
        return productType;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public BigDecimal getCostPerSqFt() {
        return costPerSqFt;
    }

    public BigDecimal getLaborCostPerSqFt() {
        return laborCostPerSqFt;
    }

    public BigDecimal getMaterialCost() {
        return materialCost;
    }

    public BigDecimal getLaborCost() {
        return laborCost;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public BigDecimal getTotal() {
        return total;
    }

}
