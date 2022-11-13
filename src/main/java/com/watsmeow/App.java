package com.watsmeow;

import com.watsmeow.controller.Controller;
import com.watsmeow.dao.*;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class App {
    public static void main(String[] args) throws PersistenceException {

        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
            appContext.scan("com.watsmeow");
            appContext.refresh();;

            Controller controller = appContext.getBean("controller", Controller.class);
            controller.run();
    }
}