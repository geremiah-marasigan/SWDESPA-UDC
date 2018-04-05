/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VIEW;

import CONTROLLER.*;
import MODEL.*;

/**
 *
 * @author ianona
 */
public class ModuleFactory {
    public ModuleView getView(String type) {
        switch (type) {
            case "DOCTOR":
                return new DoctorView();
            case "SECRETARY":
                return new SecretaryView();
            case "CLIENT":
                return new ClientView();
        }
        return null;
    }
    
    public ModuleController getController(String type, ModuleService ms, ModuleView mv) {
        switch (type) {
            case "DOCTOR":
                return new DoctorController(ms, mv);
            case "SECRETARY":
                return new SecretaryController(ms, mv);
            case "CLIENT":
                return new ClientController(ms, mv);
        }
        return null;
    }
    
    public ModuleService getModel(String type) {
        switch (type) {
            case "DOCTOR":
                return new DoctorService(new CalendarDB());
            case "SECRETARY":
                return new SecretaryService(new CalendarDB());
            case "CLIENT":
                return new ClientService(new CalendarDB());
        }
        return null;
    }
}
