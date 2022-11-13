package com.watsmeow.dao;

import com.watsmeow.dto.Order;
import com.watsmeow.dto.Product;
import com.watsmeow.dto.TaxInfo;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Stream;

@Repository
public class DaoFileImpl implements DaoFileInterface {

    private static final String DELIMITER = ",";

    // Private helper method to convert strings to BigDec
    private BigDecimal doubleToBigDec(String dub) {
        BigDecimal bigDec = BigDecimal.valueOf(Double.valueOf(dub));
        return bigDec.setScale(2, RoundingMode.HALF_UP);
    }

    public Product unmarshalProduct(String productAsText) {
        String[] productTokens = productAsText.split(DELIMITER);
        String productType = productTokens[0];
        BigDecimal costPerSqFt = doubleToBigDec(productTokens[1]);
        BigDecimal laborCostPerSqFt = doubleToBigDec(productTokens[2]);
        Product productFromFile = new Product(productType, costPerSqFt, laborCostPerSqFt);
        return productFromFile;
    }

    public TaxInfo unmarshalTaxInfo(String infoAsText) {
        String[] taxTokens = infoAsText.split(DELIMITER);
        String stateAbbrev = taxTokens[0];
        String stateName = taxTokens[1];
        BigDecimal taxRate = doubleToBigDec(taxTokens[2]);
        TaxInfo infoFromFile = new TaxInfo(stateAbbrev, stateName, taxRate);
        return infoFromFile;
    }

    public Order unmarshalOrder(LocalDate orderDate, String orderAsText) {
        String[] orderTokens = orderAsText.split(DELIMITER);
        int orderNumber = Integer.parseInt(orderTokens[0]);
        String customerName = orderTokens[1];
        String stateAbbrev = orderTokens[2];
        BigDecimal taxRate = doubleToBigDec(orderTokens[3]);
        String productType = orderTokens[4];
        BigDecimal area = doubleToBigDec(orderTokens[5]);
        BigDecimal costPerSqFt = doubleToBigDec(orderTokens[6]);
        BigDecimal laborCostPerSqFt = doubleToBigDec(orderTokens[7]);
        BigDecimal materialCost = doubleToBigDec(orderTokens[8]);
        BigDecimal laborCost = doubleToBigDec(orderTokens[9]);
        BigDecimal tax = doubleToBigDec(orderTokens[10]);
        BigDecimal total = doubleToBigDec(orderTokens[11]);
        Order orderFile = new Order(customerName, stateAbbrev, productType, area);
        orderFile.setOrderDate(orderDate);
        orderFile.setOrderNumber(orderNumber);
        orderFile.setStateAbbrev(stateAbbrev);
        orderFile.setTaxRate(taxRate);
        orderFile.setCostPerSqFt(costPerSqFt);
        orderFile.setLaborCostPerSqFt(laborCostPerSqFt);
        orderFile.setMaterialCost(materialCost);
        orderFile.setLaborCost(laborCost);
        orderFile.setTax(tax);
        orderFile.setTotal(total);
        return orderFile;
    }

    @Override
    public Map<String, Product> loadProducts(String file) throws PersistenceException {
        Scanner scanner;

        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(file)
                    )
            );
        } catch (FileNotFoundException e) {
            throw new PersistenceException("Could not load products into memory", e);
        }
        Map<String, Product> products = new HashMap<>();
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        };
        String currentLine;
        Product currentProduct;
        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentProduct = unmarshalProduct(currentLine);
            products.put(currentProduct.getProductType().toLowerCase(), currentProduct);
        }

        scanner.close();
        return products;
    }

    public Map<String, TaxInfo> loadTaxInfo(String file) throws PersistenceException {
        Scanner scanner;
        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(file)
                    )
            );

        } catch (FileNotFoundException e) {
            throw new PersistenceException("Could not load products into memory", e);
        }

        Map<String, TaxInfo> taxInfo = new HashMap<>();
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        };
        String currentLine;
        TaxInfo currentTaxInfo;
        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            currentTaxInfo = unmarshalTaxInfo(currentLine);
            taxInfo.put(currentTaxInfo.getStateName().toLowerCase(), currentTaxInfo);
        }

        scanner.close();
        return taxInfo;
    }

    public Map<Integer, Order> loadOrder(String file) throws PersistenceException {
        Map<Integer, Order> order = new HashMap<>();
        File dir = new File(file);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                Scanner scanner;

                try {
                    scanner = new Scanner(
                            new BufferedReader(
                                    new FileReader(child)
                            )
                    );
                } catch (FileNotFoundException e) {
                    throw new PersistenceException("Could not load products into memory", e);
                }
                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                };
                String currentLine;
                Order currentOrder;
                LocalDate orderDate = LocalDate.parse(child.getName().substring(7, 15), DateTimeFormatter.ofPattern("MMddyyyy"));
                while (scanner.hasNextLine()) {
                    currentLine = scanner.nextLine();
                    currentOrder = unmarshalOrder(orderDate, currentLine);
                    order.put(currentOrder.getOrderNumber(), currentOrder);
                }

                scanner.close();
            }
        } else {
            throw new PersistenceException("Could not find directory");
        }
        return order;
    }


    public String marshalProduct(Product product) {
        String productAsText = product.getProductType() + DELIMITER;
        productAsText += product.getCostPerSqFt() + DELIMITER;
        productAsText += product.getLaborCostPerSqFt();
        return productAsText;
    }


    public String marshalTaxInfo(TaxInfo taxInfo) {
        String taxInfoAsText = taxInfo.getStateAbbrev() + DELIMITER;
        taxInfoAsText += taxInfo.getStateName() + DELIMITER;
        taxInfoAsText += taxInfo.getTaxRate();
        return taxInfoAsText;
    }

    public String marshalOrder(Order order) {
        String orderInfoAsText = order.getOrderNumber() + DELIMITER;
        orderInfoAsText += order.getCustomerName() + DELIMITER;
        orderInfoAsText += order.getStateAbbrev() + DELIMITER;
        orderInfoAsText += order.getTaxRate() + DELIMITER;
        orderInfoAsText += order.getProductType() + DELIMITER;
        orderInfoAsText += order.getArea() + DELIMITER;
        orderInfoAsText += order.getCostPerSqFt() + DELIMITER;
        orderInfoAsText += order.getLaborCostPerSqFt() + DELIMITER;
        orderInfoAsText += order.getMaterialCost() + DELIMITER;
        orderInfoAsText += order.getLaborCost() + DELIMITER;
        orderInfoAsText += order.getTax() + DELIMITER;
        orderInfoAsText += order.getTotal();
        return orderInfoAsText;
    }

    public void writeOrder(Order order) throws PersistenceException {
        String newOrderEntry = marshalOrder(order);
        String basic = order.getOrderDate().format(DateTimeFormatter.BASIC_ISO_DATE);
        String year = basic.substring(0,4);
        String monthDate = basic.substring(4,8);
        String fileName = "Orders_" +  monthDate + year;
        File file = new File("src/Orders/" + fileName + ".txt");
        boolean exists = file.exists();
        PrintWriter out;
        try {
            /* Setting the second parameter to "true" opens the audit file in append mode ensures each entry will be
             * appended to the file rather than overwriting what was there before
             */
            out = new PrintWriter(new FileWriter(file, true));
        } catch (Exception e) {
            throw new PersistenceException("Could not persist audit information", e);
        }
        if (!exists) {
            out.println("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot," +
                    "LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total");
        }
        out.println(newOrderEntry);
        out.flush();
        out.close();
    }

    public void writeAllOrders(List<Order> orderList) throws PersistenceException {
        Order order = orderList.get(0);
        String basic = order.getOrderDate().format(DateTimeFormatter.BASIC_ISO_DATE);
        String year = basic.substring(0,4);
        String monthDate = basic.substring(4,8);
        String fileName = "Orders_" +  monthDate + year;
        File file = new File("src/Orders/" + fileName + ".txt");
        PrintWriter out;
        try {
            /* Setting the second parameter to "true" opens the audit file in append mode ensures each entry will be
             * appended to the file rather than overwriting what was there before
             */
            out = new PrintWriter(new FileWriter(file, false));
        } catch (Exception e) {
            throw new PersistenceException("Could not persist audit information", e);
        }

        out.println("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot," +
                "LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total");
        for (Order currentOrder : orderList) {
            String updatedEntry = marshalOrder(currentOrder);
            out.println(updatedEntry);
        }
        out.flush();
        out.close();
    }

    public void deleteOrderFile(LocalDate date) throws PersistenceException {
        String basic = date.format(DateTimeFormatter.BASIC_ISO_DATE);
        String year = basic.substring(0,4);
        String monthDate = basic.substring(4,8);
        String fileName = "Orders_" +  monthDate + year;
        File file = new File("src/Orders/" + fileName + ".txt");
        try {
            if(file.delete()) {
                System.out.println(file.getName() + " deleted");   //getting and printing the file name
            } else {
                System.out.println("Deletion failed");
            }
        }
        catch(Exception e) {
            throw new PersistenceException("Could not persist audit information", e);
        }
    }

    @Override
    public void writeToExportAllData() throws PersistenceException {
        File[] directoryListing = new File("src/Orders").listFiles();
        if (directoryListing != null) {
            PrintWriter out;
            File file = new File("src/Backup/DataExport.txt");
            try {
                out = new PrintWriter(new FileWriter(file, false));
            } catch (IOException e) {
                throw new PersistenceException("Could not load products into memory", e);
            }
            out.println("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,CostPerSquareFoot," +
                    "LaborCostPerSquareFoot,MaterialCost,LaborCost,Tax,Total, Order Date");
            for (File child : directoryListing) {
                Scanner scanner;

                try {
                    scanner = new Scanner(
                            new BufferedReader(
                                    new FileReader(child)
                            )
                    );

                } catch (FileNotFoundException e) {
                    throw new PersistenceException("Could not load products into memory", e);
                }
                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                };
                String currentLine;
                LocalDate orderDate = LocalDate.parse(child.getName().substring(7, 15), DateTimeFormatter.ofPattern("MMddyyyy"));
                while (scanner.hasNextLine()) {
                    currentLine = scanner.nextLine();
                    out.println(currentLine + "," + orderDate);
                }

                scanner.close();
            }
            out.flush();
            out.close();
        } else {
            throw new PersistenceException("Could not find directory");
        }
    }

}
