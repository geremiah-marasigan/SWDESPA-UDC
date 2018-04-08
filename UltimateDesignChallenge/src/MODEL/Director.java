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
public class Director {
    private TimeslotBuilder builder;
    
    public void setTimeslotBuilder (TimeslotBuilder builder, ModuleController mc) {
        this.builder = builder;
        builder.setController(mc);
    }
    
    public boolean addAppSlot(String name, String startDay, String endDay, String repeat, int startTime, int endTime) {
        builder.createAppointment(name, startDay, endDay, repeat, startTime, endTime);
        Appointment app = builder.getAppointment();
        
        if (builder.isAvailable(app)) {
            if (builder.getController() instanceof DoctorController){
                System.out.print("Entered builder");
                ((AppointmentSlotBuilder)builder).addAppSlot(app);
            }
            else if (builder.getController() instanceof ClientController){
                ((AppointmentBuilder)builder).addAppSlot(app);
            }
            return true;
        }
        
        return false;
    }
}
