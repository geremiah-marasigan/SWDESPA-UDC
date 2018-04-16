/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MODEL;

import CONTROLLER.*;
import VIEW.AgendaItem;
import VIEW.ScheduleItem;
import java.sql.Connection;
import java.util.List;

/**
 *
 * @author ianona
 */
public class Director {

    private TimeslotBuilder builder;

    public void setTimeslotBuilder(TimeslotBuilder builder, ModuleController mc) {
        this.builder = builder;
        builder.setController(mc);
    }

    public boolean addAppSlot(List<Appointment> appSlots) {
        // CHECKS IF ALL SlOTS CAN BE ADDED
        for (Appointment app : appSlots) {
            if (!builder.isAvailable(app)) {
                return false;
            }
        }

        // ADDS ALL SLOTS IF POSSIBLE
        for (Appointment app : appSlots) {
            ((AppointmentSlotBuilder) builder).addAppSlot(app);
        }
        return true;
    }

    public boolean addApp(Appointment apps) {
        ((AppointmentBuilder) builder).setSwap(true);
        if (!builder.isAvailable(apps)) {
            return false;
        }
        // ADDS ALL SLOTS IF POSSIBLE
        ((AppointmentBuilder) builder).addAppSlot(apps);

        return true;
    }

    public boolean addApp(List<Appointment> apps) {
        ((AppointmentBuilder) builder).setSwap(false);
        for (Appointment app : apps) {
            if (!builder.isAvailable(app)) {
                return false;
            }
        }

        // ADDS ALL SLOTS IF POSSIBLE
        for (Appointment app : apps) {
            ((AppointmentBuilder) builder).addAppSlot(app);
        }
        return true;
    }
}
