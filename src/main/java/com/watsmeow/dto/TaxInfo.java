package com.watsmeow.dto;

import java.math.BigDecimal;

public class TaxInfo {

    private String stateAbbrev;

    private String stateName;

    private BigDecimal taxRate;

    public TaxInfo(String stateAbbrev, String stateName, BigDecimal taxRate) {
        this.stateAbbrev = stateAbbrev;
        this.stateName = stateName;
        this.taxRate = taxRate;
    }

    public String getStateAbbrev() {
        return stateAbbrev;
    }

    public String getStateName() {
        return stateName;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }
}
