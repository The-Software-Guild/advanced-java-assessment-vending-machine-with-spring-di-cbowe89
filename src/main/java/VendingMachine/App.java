package VendingMachine;

import VendingMachine.controller.Controller;
import VendingMachine.dao.PersistenceException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
    public static void main(String[] args) throws PersistenceException {
        AnnotationConfigApplicationContext appContext =
                new AnnotationConfigApplicationContext();
        appContext.scan("VendingMachine");
        appContext.refresh();
        Controller controller = appContext.getBean("controller", Controller.class);
        // Run the controller
        controller.run();
    }
}
