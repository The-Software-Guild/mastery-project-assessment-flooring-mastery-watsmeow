package com.watsmeow.view;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Class containing all methods to communicate with the user, both writing to the console and reading the user's input
 */
public interface UserIO {

    /**
     * Method to print a message to the user
     */
    void print(String msg);

    /**
     * Method to read a string input from user
     */
    String readString(String msg);


    /**
     * Method to read area input from user
     */
    BigDecimal readArea(String msg);

    /**
     * Method to read date input from user
     */
    LocalDate readDate(String msg);

    /**
     * Method to read number input from user
     */
    int readNum(String msg);

    /**
     * Method to get menu selection from user
     */
    int readSelection(String msg, int min, int max);

}
