package VendingMachine;

import VendingMachine.controller.Controller;
import VendingMachine.dao.AuditDao;
import VendingMachine.dao.AuditDaoImpl;
import VendingMachine.dao.VendingMachineDao;
import VendingMachine.dao.VendingMachineDaoFileImpl;
import VendingMachine.dao.PersistenceException;
import VendingMachine.service.ServiceLayer;
import VendingMachine.service.ServiceLayerImpl;
import VendingMachine.ui.UserIO;
import VendingMachine.ui.UserIOConsoleImpl;
import VendingMachine.ui.View;

public class App {
    public static void main(String[] args) throws PersistenceException {
        // Instantiate UserIO implementation
        UserIO io = new UserIOConsoleImpl();

        // Instantiate View, wire the UserIO implementation into it
        View view = new View(io);

        // Instantiate DAO
        VendingMachineDao vendingMachineDao = new VendingMachineDaoFileImpl();

        // Instantiate the Audit DAO
        AuditDao auditDao = new AuditDaoImpl();

        // Instantiate Service Layer, wire DAO and Audit DAO into it
        ServiceLayer service =
                new ServiceLayerImpl(vendingMachineDao, auditDao);

        // Instantiate Controller, wire Service Layer into it
        Controller controller =
                new Controller(view, service);

        // Run the controller
        controller.run();
    }
}
