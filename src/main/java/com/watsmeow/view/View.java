package com.watsmeow.view;

import com.watsmeow.dto.Order;
import com.watsmeow.dto.Product;
import com.watsmeow.dto.TaxInfo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.List;

public class View {

    private UserIO io;

    public View(UserIO io) {
        this.io = io;
    }

    public int printMenuGetUserSelection() {

        io.print("<<FLOORING ORDER MANAGEMENT SYSTEM>>");
        io.print("1. Display Orders");
        io.print("2. Add an Order");
        io.print("3. Edit an Order");
        io.print("4. Remove an Order");
        io.print("5. Export All Data");
        io.print("6. Quit");

        return io.readSelection("Select from the following choices:", 1, 6);
    }

    public LocalDate getOrderDate() {
        try {
            return io.readDate("Enter the date of the orders you wish to view in MM/dd/yyyy format");
        } catch (DateTimeParseException e) {
            throw e;
        }
    }

    public String displayOrderInformation(Order order) {
        String orderInfo = String.format(" Order Date: %s,\n Order Number: %s,\n Customer Name: %s,\n State: %s,\n" +
                        " Tax Rate: $%s,\n Product Type: %s,\n Area: %s,\n Cost per Sq Ft: $%s,\n " +
                        "Labor Cost per Sq Ft: $%s,\n Material Cost: $%s,\n Labor Cost: $%s,\n Tax: $%s,\n " +
                        "Total: $%s,\n",
                order.getOrderDate(),
                order.getOrderNumber(),
                order.getCustomerName(),
                order.getState(),
                order.getTaxRate(),
                order.getProductType(),
                order.getArea(),
                order.getCostPerSqFt(),
                order.getLaborCostPerSqFt(),
                order.getMaterialCost(),
                order.getLaborCost(),
                order.getTax(),
                order.getTotal());
        return orderInfo;
    }
    public void displayOrderList(List<Order> orderList) {
        for (Order currentOrder : orderList) {
            String orderInfo = displayOrderInformation(currentOrder);
            io.print(orderInfo);
        }
    }

    public void ordersDoNotExistBanner() {
        io.print("No orders exist for the parameter(s) you have provided.");
    }

    public void displayProductList(List<Product> productList) {
        for (Product currentProduct : productList) {
            String productInfo = String.format("Name: %s : Price per Sq Ft: %s",
                    currentProduct.getProductType(),
                    currentProduct.getCostPerSqFt());
            io.print(productInfo);
        }
    }

    public void displayTaxInfoList(List<TaxInfo> taxInfoList) {
        for (TaxInfo currentInfo : taxInfoList) {
            String taxInfo = String.format("State: %s : Tax Rate: %s",
                    currentInfo.getStateName(),
                    currentInfo.getTaxRate());
            io.print(taxInfo);
        }
    }

    public Order getNewOrderInfo(List<Product> productList, List<TaxInfo> taxInfoList) {
        LocalDate orderDate;
        do {
            try {
                orderDate = io.readDate("Enter the future date of your new order in MM/dd/yyyy format");
                if (orderDate.isBefore(LocalDate.now(ZoneId.of("America/Montreal")))) {
                    io.print("Date must be in the future");
                }
            } catch (DateTimeParseException e) {
                io.print("Invalid date input");
                orderDate = LocalDate.now().minusDays(1);
            }
        } while (orderDate.isBefore(LocalDate.now(ZoneId.of("America/Montreal"))));
        String customerName = io.readString("Enter your name");

        // do while and try catch for state name to catch if state info is not available
        displayTaxInfoList(taxInfoList);
        String state;
        do {
            state = io.readString("Enter the state where the order will be installed");
            if (!taxInfoList.contains(state)) {
                io.print("Service unavailable in that state, choose a state from the list");
            }
        } while (!taxInfoList.contains(state));



        displayProductList(productList);
        // do while and try catch for product name to catch if product info is not available
        String productType = io.readString("Enter the product name");

        // do while and try catch for area to catch if area is not big enough
        BigDecimal area = io.readArea("Enter the project area in square feet, min order 100 sq ft.");
        Order newOrder = new Order(customerName, state, productType, area);
        newOrder.setOrderDate(orderDate);
        return newOrder;
    }

    public boolean confirmNewOrderBanner(Order order) {
        String orderInfo = displayOrderInformation(order);
            io.print(orderInfo);
        String userInput = io.readString("To confirm this order press Y, to cancel press N");
        if (userInput.toUpperCase().equals("Y")) {
            return true;
        } else {
            return false;
        }
    }


    public void unknownCommandBanner() {
        io.print("Unknown command");
    }

    public void invalidInputBanner() {
        io.print("Invalid input");
    }

    public void exitBanner() {
        io.print("Toodles.");
    }
}
