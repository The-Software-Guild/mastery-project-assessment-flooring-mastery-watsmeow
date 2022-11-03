package com.watsmeow.view;

import com.watsmeow.dto.Order;
import com.watsmeow.dto.Product;
import com.watsmeow.dto.TaxInfo;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

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
            return io.readDate("Enter the date of the order(s) you wish to view in MM/dd/yyyy format");
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

    public void editFieldBanner() {
        io.print("Edit this field by entering in update information here, " +
                "or press enter to continue on to the next field");
    }

    public int getOrderNumber() {
        return io.readNum("Enter the order number");
    }

    public Order editExistingOrder(Order order, List<Product> productList, List<TaxInfo> taxInfoList) {
        String customerName = String.format("Customer Name: %s",
                order.getCustomerName());
            io.print(customerName);
        String newName = io.readString("To change the customer name, " +
                "enter a new name here or hit enter if you do not wish to update");

        String state = String.format("State: %s",
                order.getState());
        io.print(state);
        String newState = validateState(taxInfoList, false);

        String product = String.format("Product: %s",
                order.getProductType());
        io.print(product);
        String newProduct = validateProductName(productList, false);

        String area = String.format("Area: %s",
                order.getArea());
        io.print(area);
        BigDecimal newArea = validateAreaSize(false);
        order.setCustomerName(newName);
        order.setState(newState);
        order.setProductType(newProduct);
        order.setArea(newArea);
        displayOrderInformation(order);
        return order;
    }

    public LocalDate validateFutureDate() {
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
        return orderDate;
    }

    public String validateState(List<TaxInfo> taxInfoList, boolean isCreate) {
        displayTaxInfoList(taxInfoList);
        List<String> stateNames = taxInfoList
                .stream()
                .map(taxInfo -> taxInfo.getStateName().toLowerCase())
                .collect(Collectors.toList());
        String state;
        do {
            state = io.readString("Enter the state where the order will be installed");
            if ((!isCreate && !state.equals("")) && !stateNames.contains(state.toLowerCase())) {
                io.print("Service unavailable in that state, choose a state from the list");
            }
        } while ((!isCreate && !state.equals("")) && !stateNames.contains(state.toLowerCase()));
        return state;
    }

    public String validateProductName(List<Product> productList, boolean isCreate) {
        displayProductList(productList);
        List<String> productNames = productList
                .stream()
                .map(productName -> productName.getProductType().toLowerCase())
                .collect(Collectors.toList());
        String productType;
        do {
            productType = io.readString("Enter the product name");
            if ((!isCreate && !productNames.equals("")) && !productNames.contains(productType.toLowerCase())) {
                io.print("Only products from the list are available for purchase");
            }
        } while ((!isCreate && !productNames.equals("")) && !productNames.contains(productType.toLowerCase()));
        return productType;
    }

    public BigDecimal validateAreaSize(boolean isCreate) {
        BigDecimal area;
        do {
            area = io.readArea("Enter the project area in square feet, min order 100 sq ft");
            if ((!isCreate && !area.equals("")) && area.compareTo(BigDecimal.valueOf(100)) < -1) {
                io.print("Your order must be larger than 100 sq ft");
            }
        } while ((!isCreate && !area.equals("")) &&  area.compareTo(BigDecimal.valueOf(100)) <= -1);
        return area;
    }
    public Order getNewOrderInfo(List<Product> productList, List<TaxInfo> taxInfoList) {
        LocalDate orderDate = validateFutureDate();
        String customerName = io.readString("Enter your name");
        String state = validateState(taxInfoList, true);
        String productType = validateProductName(productList, true);
        BigDecimal area = validateAreaSize(true);
        Order newOrder = new Order(customerName, state, productType, area);
        newOrder.setOrderDate(orderDate);
        return newOrder;
    }

    public boolean confirmOrderInformationBanner(Order order) {
        String orderInfo = displayOrderInformation(order);
            io.print(orderInfo);
        String userInput = io.readString("To confirm this order press Y, to cancel press N");
        if (userInput.toUpperCase().equals("Y")) {
            return true;
        } else {
            return false;
        }
    }

    public void orderSuccessfullyEditedBanner() {
        io.print("Order edited successfully");
    }
    public void orderSuccessfullyPlacedBanner() {
        io.print("Order placed successfully");
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
