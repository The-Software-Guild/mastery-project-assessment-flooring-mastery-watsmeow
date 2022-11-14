package com.watsmeow.view;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

@Component
public class UserIOImpl implements UserIO {
    @Override
    public void print(String msg) {
        System.out.println(msg);
    }

    // Reads user string input
    @Override
    public String readString(String msg) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(msg);
        String userStringInput = scanner.nextLine();
        return userStringInput;
    }

    // Reads user area input
    @Override
    public BigDecimal readArea(String msg) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(msg);
        double userInput = scanner.nextDouble();
        BigDecimal userAreaInput = new BigDecimal(userInput);
        userAreaInput.setScale(2, RoundingMode.HALF_UP);
        return userAreaInput;
    }

    // Reads user date input
    @Override
    public LocalDate readDate(String msg) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(msg);
        LocalDate ld;
        try {
            ld = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        } catch (DateTimeParseException e) {
            throw e;
        }
        return ld;
    }

    // Reads user num input
    public int readNum(String msg) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(msg);
        int userNumSelection = scanner.nextInt();
        return userNumSelection;
    }

    // Reads user menu selection
    @Override
    public int readSelection(String msg, int min, int max) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(msg);
        int userSelection = min -1;
        while (userSelection < min || userSelection > max) {
            try {
                userSelection = scanner.nextInt();
                if (userSelection < min || userSelection > max) {
                    System.out.println("Invalid input");
                }
            } catch(Exception e) {
                return -1;
            }
        }
        return userSelection;
    }
}
