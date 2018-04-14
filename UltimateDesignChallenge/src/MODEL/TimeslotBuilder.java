/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MODEL;

import CONTROLLER.*;

/**
 *
 * @author ianona
 */
public abstract class TimeslotBuilder {
    protected ModuleController controller;
    protected Appointment app;
    
    public void setController (ModuleController mc) {
        controller = mc;
    }
    
    public ModuleController getController () {
        return controller;
    }
    
    public Appointment getAppointment () {
        return app;
    }
    
    public void createAppointment(String name, String startDay, String endDay, String repeat, int startTime, int endTime) {
        app = new Appointment(name, startDay, endDay, repeat, startTime, endTime);
    }
    
    public abstract boolean isAvailable (Appointment app);
    
}
