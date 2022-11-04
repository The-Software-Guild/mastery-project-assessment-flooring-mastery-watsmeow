package com.watsmeow.controller;

import com.watsmeow.dao.PersistenceException;
import com.watsmeow.dto.Order;
import com.watsmeow.dto.Product;
import com.watsmeow.dto.TaxInfo;
import com.watsmeow.service.ServiceInterface;
import com.watsmeow.service.ValidationException;
import com.watsmeow.view.View;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

public class Controller {

    private View view;

    private ServiceInterface service;

    public Controller(View view, ServiceInterface service) {
        this.view = view;
        this.service = service;
    }

    public void run() throws PersistenceException, ValidationException {
        boolean keepRunning = true;
        int userSelection = 0;

        try {
            // Places the running into a try and catch so that if there is an error it alerts properly
            while (keepRunning) {

                userSelection = getUserMenuSelection();

                // Switch statement calls methods that run based on user selection
                switch (userSelection) {
                    case 1:
                        displayOrdersOfGivenDate();
                        break;
                    case 2:
                        addOrder();
                        break;
                    case 3:
                        editOrder();
                        break;
                    case 4:
                        removeOrder();
                        break;
                    case 5:
//                        exportData();
                        break;
                    case 6:
                        keepRunning = false;
                        break;
                    default:
                        unknownCommand();
                }
            }
            // Display the exit message if user exits program
            exitMessage();
        } catch (ValidationException e) {
            view.invalidInputBanner();
        }
    }

    private int getUserMenuSelection() {
        return view.printMenuGetUserSelection();
    }

    private void displayOrdersOfGivenDate() throws PersistenceException {
        try {
            LocalDate date = view.getOrderDate();
            List<Order> ordersList = service.getOrdersByDate(date);
            if (ordersList.size() > 0) {
                view.displayOrderList(ordersList);
            } else {
                view.ordersDoNotExistBanner();
            }
        } catch (DateTimeParseException e) {
            view.invalidInputBanner();
        }
    }

    private void addOrder() throws ValidationException, PersistenceException {
        List<Product> productList = service.getAllProducts();
        List<TaxInfo> taxInfoList = service.getAllTaxInfo();
        Order order = view.getNewOrderInfo(productList, taxInfoList);
        order = service.generateFullOrder(order);
        if (view.confirmOrderInformationBanner(order)) {
            service.saveOrder(order);
        }
        view.actionCompletedSuccessfullyBanner();
    }

    private void editOrder() throws PersistenceException, ValidationException {
        List<Product> productList = service.getAllProducts();
        List<TaxInfo> taxInfoList = service.getAllTaxInfo();
        view.editFieldBanner();
        Optional<Order> optionalOrder = service.getOrderToEditOrDeleteOrder(view.getOrderDate(), view.getOrderNumber());
        if (optionalOrder.isEmpty()) {
            view.ordersDoNotExistBanner();
            editOrder();
        } else {
            Order order = optionalOrder.get();
            order = view.editExistingOrder(order, productList, taxInfoList);
            order = service.generateFullOrder(order);
            view.displayOrderInformation(order);
            if (view.confirmOrderInformationBanner(order)) {
                service.editOrder(order);
            }
            view.actionCompletedSuccessfullyBanner();
        }
    }

    private void removeOrder() throws PersistenceException {
        Optional<Order> optionalOrder = service.getOrderToEditOrDeleteOrder(view.getOrderDate(), view.getOrderNumber());
        if (optionalOrder.isEmpty()) {
            view.ordersDoNotExistBanner();
            removeOrder();
        } else {
            Order order = optionalOrder.get();
            view.displayOrderInformation(order);
            if (view.confirmOrderInformationBanner(order)) {
                service.deleteOrder(order);
            }
        }
    }

    private void unknownCommand() {
        view.unknownCommandBanner();
    }

    private void exitMessage() {
        view.exitBanner();
    }
}
