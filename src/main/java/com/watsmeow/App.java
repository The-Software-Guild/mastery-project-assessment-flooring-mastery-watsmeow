package com.watsmeow;

import com.watsmeow.controller.Controller;
import com.watsmeow.dao.*;
import com.watsmeow.service.ServiceImpl;
import com.watsmeow.service.ServiceInterface;
import com.watsmeow.service.ValidationException;
import com.watsmeow.view.UserIO;
import com.watsmeow.view.UserIOImpl;
import com.watsmeow.view.View;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {
    public static void main(String[] args) throws PersistenceException, ValidationException {
//        UserIO io = new UserIOImpl();
//        View view = new View(io);
//        DaoInterface dao = new DaoImpl();
//        DaoAuditInterface auditDao = new DaoAuditImpl();
//        ServiceInterface service = new ServiceImpl(dao, auditDao);
//        Controller controller = new Controller(view, service);
//        controller.run();

//        ApplicationContext ctx =
//                new ClassPathXmlApplicationContext("applicationContext.xml");
//        Controller controller =
//                ctx.getBean("controller", Controller.class);
//        controller.run();

        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
            appContext.scan("come.watsmeow");
            appContext.refresh();;

            Controller controller = appContext.getBean("controller", Controller.class);
            controller.run();
    }
}