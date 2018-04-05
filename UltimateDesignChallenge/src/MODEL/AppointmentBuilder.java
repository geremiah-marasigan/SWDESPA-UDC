/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MODEL;

/**
 *
 * @author ianona
 */
public class AppointmentBuilder extends TimeslotBuilder{

    @Override
    public boolean isAvailable(Appointment app) {
        // DO CHECKING HERE
        return true;
    }
}
