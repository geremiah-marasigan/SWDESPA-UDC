/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MODEL;

import CONTROLLER.*;
import VIEW.AgendaItem;
import VIEW.ScheduleItem;
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

    public boolean addAppSlot(String name, String startDay, String endDay, String repeat, int startTime, int endTime) {
        builder.createAppointment(name, startDay, endDay, repeat, startTime, endTime);
        Appointment app = builder.getAppointment();

        if (builder.isAvailable(app)) {
            if (builder.getController() instanceof DoctorController) {
                ((AppointmentSlotBuilder) builder).addAppSlot(app);
            } else if (builder.getController() instanceof SecretaryController) {
                ((AppointmentBuilder) builder).addAppSlot(app);
            }

            return true;
        }

        return false;
    }

    public boolean addApp(String name, String startDay, String endDay, String repeat, int startTime, int endTime) {
        builder.createAppointment(name, startDay, endDay, repeat, startTime, endTime);
        Appointment app = builder.getAppointment();

        if (builder.isAvailable(app)) {
            ((AppointmentBuilder) builder).addAppSlot(app);

            return true;
        }

        return false;
    }

    public void addAgendaPanels(List<AgendaItem> items, List<Appointment> apps, User user, String date, List<Appointment> delapps, List<Appointment> taken) {
        for (int i = 0; i < apps.size(); i++) {
            if (((AppointmentSlotBuilder) builder).isValidSlot(apps.get(i), user, date)) {
                int start = apps.get(i).getStartTime();
                while (start < apps.get(i).getEndTime()) {
                    if (!((AppointmentSlotBuilder) builder).isDeleted(date, start, delapps)) {
                        boolean booked = ((AppointmentSlotBuilder) builder).isBookedSlot(date, start, delapps, taken);
                        items.add(new AgendaItem(builder.getController(), apps.get(i), start, booked));
                    }
                    start += 30;
                    if (start % 100 >= 60) {
                        start = ((start / 100) + 1) * 100;
                    }
                    if (start / 100 >= 24) {
                        start = 0;
                    }
                }
            }
        }
    }

    public void addSchedulePanels(List<ScheduleItem> items, List<Appointment> apps, User user, String curDate, List<Appointment> delapps, List<Appointment> taken, List<String> doctors) {
        for (int hour = 0; hour < 24; hour++) {
            for (int min = 0; min < 60; min += 30) {
                String hourString = String.valueOf(hour);
                if (hourString.length() == 1) {
                    hourString = "0" + hourString;
                }
                String minString = String.valueOf(min);
                if (minString.length() == 1) {
                    minString = "0" + minString;
                }
                String time = hourString + ":" + minString;
                int timecheck = Integer.valueOf(time.replace(":", ""));

                boolean good = false;
                for (Appointment app : apps) {
                    if (((AppointmentSlotBuilder) builder).isValid(timecheck, curDate, app, delapps)) {
                        if (((AppointmentSlotBuilder) builder).isSlot(app, doctors)) {
                            if (((AppointmentSlotBuilder) builder).isBooked(curDate, timecheck, taken, delapps)) {
                                items.add(new ScheduleItem(builder.getController(), time, app, user.getLastname(), curDate, true, ((AppointmentSlotBuilder) builder).getTarget(timecheck, taken)));
                            } else {
                                items.add(new ScheduleItem(builder.getController(), time, app, user.getLastname(), curDate, false, null));
                            }
                        } else {
                            items.add(new ScheduleItem(builder.getController(), time, app, user.getLastname(), curDate, true, null));
                        }
                        good = true;
                        break;
                    }
                }

                if (!good) {
                    items.add(new ScheduleItem(builder.getController(), time));
                }
            }
        }
    }
}
