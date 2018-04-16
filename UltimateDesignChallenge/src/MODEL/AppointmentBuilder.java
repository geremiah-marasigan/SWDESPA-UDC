/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MODEL;

import CONTROLLER.ClientController;
import CONTROLLER.SecretaryController;
import java.util.List;

/**
 *
 * @author ianona
 */
public class AppointmentBuilder extends TimeslotBuilder {

    @Override
    public boolean isAvailable(Appointment app) {
        List<Appointment> free = ((ClientController)controller).getAllFree(app.getDate());
        if (inTime(app,free))
            return true;
        System.out.println("ERROR IN AVAILABLE");
        return false;
    }
    
    private boolean inTime(Appointment app,List<Appointment> free){
        for (Appointment freeSlot: free){
            System.out.println(app.getTime() + " COMPARED TO " + freeSlot.getTime());
            System.out.println(app.getName() + " COMPARED TO " + freeSlot.getName());
            if (app.getTime() == freeSlot.getTime() && app.getName().equals(freeSlot.getName()))
                return true;
        }
        System.out.println("ERROR IN TIME");
        return false;
    }
    
    public void addAppSlot(Appointment app) {
        if (controller instanceof ClientController) {
            System.out.println(app.getTaken() + app.getName() + app.getDate() +" " + app.getTime());
            ((ClientController) controller).addAppointment(app);
        } else if (controller instanceof SecretaryController) {
            //((SecretaryController) controller).addAppointment(app);
        }
    }
}
