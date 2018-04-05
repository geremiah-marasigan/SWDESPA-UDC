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
public class AppointmentSlotBuilder extends TimeslotBuilder{

    @Override
    public boolean isAvailable(Appointment app) {
        // DO CHECKING HERE
        return true;
    }
    
    public void addAppSlot (Appointment app) {
        if (controller instanceof DoctorController) {
            ((DoctorController)controller).addAppointment(app);
        }
    }

}
