package com.watsmeow;

import com.watsmeow.controller.Controller;
import com.watsmeow.dao.*;
import com.watsmeow.service.ServiceImpl;
import com.watsmeow.service.ServiceInterface;
import com.watsmeow.service.ValidationException;
import com.watsmeow.view.UserIO;
import com.watsmeow.view.UserIOImpl;
import com.watsmeow.view.View;

public class App {
    public static void main(String[] args) throws PersistenceException, ValidationException {
        UserIO io = new UserIOImpl();
        View view = new View(io);
        DaoInterface dao = new DaoImpl();
        DaoAuditInterface auditDao = new DaoAuditImpl();
        ServiceInterface service = new ServiceImpl(dao, auditDao);
        Controller controller = new Controller(view, service);
        controller.run();
    }
}