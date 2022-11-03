package com.watsmeow.dto;

import java.math.BigDecimal;

public class Product {

    private String productType;

    private BigDecimal costPerSquareFoot;

    private BigDecimal laborCostPerSquareFoot;

    public Product(String productType, BigDecimal costPerSquareFoot, BigDecimal laborCostPerSquareFoot) {
        this.productType = productType;
        this.costPerSquareFoot = costPerSquareFoot;
        this.laborCostPerSquareFoot = laborCostPerSquareFoot;
    }

    public String getProductType() {
        return productType;
    }

    public BigDecimal getCostPerSqFt() {
        return costPerSquareFoot;
    }

    public BigDecimal getLaborCostPerSqFt() {
        return laborCostPerSquareFoot;
    }

}
