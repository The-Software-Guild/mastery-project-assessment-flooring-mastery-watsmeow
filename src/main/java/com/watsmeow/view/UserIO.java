package com.watsmeow.view;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public interface UserIO {

    void print(String msg);

    String readString(String msg);

    BigDecimal readArea(String msg);

    LocalDate readDate(String msg);

    int readNum(String msg);

    int readSelection(String msg, int min, int max);

}
