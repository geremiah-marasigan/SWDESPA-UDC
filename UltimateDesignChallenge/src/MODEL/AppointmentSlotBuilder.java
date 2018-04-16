/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MODEL;

import CONTROLLER.*;
import java.util.List;

/**
 *
 * @author ianona
 */
public class AppointmentSlotBuilder extends TimeslotBuilder {

    @Override
    public boolean isAvailable(Appointment app) {
        List<Appointment> apps = ((DoctorController) controller).getSlots(app.getDate(), ((DoctorController)controller).getUser());

        for (Appointment slot : apps) {
            // FALSE IF YOU TRY TO SET A SLOT WHEN YOU ALREADY HAVE ONE
            if (app.getTime() == slot.getTime())
                return false;
            // FALSE IF YOU TRY TO SET A SLOT IN A BOOKED SLOT
            /*
            else if (app.getTime() == slot.getTime() && !slot.getTaken().equals("NOT_TAKEN"))
                return false;
            */
        }

        return true;
    }

    public void addAppSlot(Appointment app) {
        if (controller instanceof DoctorController) {
            ((DoctorController) controller).addAppointment(app);
        }
    }
    
    /*
    public boolean overlap(Appointment a, Appointment a2) {
        int i = 0;

        // CASE: SAME TIME SLOT
        if ((a2.getStartTime() == a.getStartTime()) && (a2.getEndTime() == a.getEndTime())) {
            return true;
        }
        // CASE: EVENT IS WITHIN
        if ((a2.getStartTime() <= a.getStartTime() && a2.getEndTime() >= a.getEndTime())) {
            return true;
        }
        // CASE: EVENT IS WITHOUT
        if ((a2.getStartTime() >= a.getStartTime() && a2.getEndTime() <= a.getEndTime())) {
            return true;
        }
        //CASE: NORMAL OVERLAP
        if ((a2.getStartTime() < a.getStartTime() && a2.getEndTime() > a.getStartTime()) || (a2.getStartTime() < a.getEndTime() && a2.getEndTime() > a.getEndTime())) {
            return true;
        }

        return false;
    }

    public boolean checkDate(String startDay, String endDay, String testDayStart, String testDayEnd) {
        try {
            Date min = new SimpleDateFormat("M/d/yyyy").parse(startDay);
            Date max = new SimpleDateFormat("M/d/yyyy").parse(endDay);
            Date curStart = new SimpleDateFormat("M/d/yyyy").parse(testDayStart);
            Date curEnd = new SimpleDateFormat("M/d/yyyy").parse(testDayEnd);

            // checks if cur between min and max, inclusive
            if (min.compareTo(curStart) * curStart.compareTo(max) >= 0) {
                System.out.println("START DAY IS WITHIN BOUNDS");
            }
            if (min.compareTo(curEnd) * curEnd.compareTo(max) >= 0) {
                System.out.println("END DAY IS WITHIN BOUNDS");
            }
            return (min.compareTo(curStart) * curStart.compareTo(max) >= 0) || (min.compareTo(curEnd) * curEnd.compareTo(max) >= 0);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean isValidSlot(Appointment app, User user, String date) {
        if (app.getName().equalsIgnoreCase(user.getLastname()) && checkDate2(app.getStartDay(), app.getEndDay(), date, app.getRepeat())) {
            return true;
        }
        return false;
    }

    public boolean isBookedSlot(String date, int start, List<Appointment> deleted, List<Appointment> taken) {
        if (!isDeleted(date, start, deleted)) {
            if (isBooked(date, start, taken, deleted)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public boolean isDeleted(String date, int time, List<Appointment> deleted) {
        for (Appointment a : deleted) {
            if (a.getStartDay().equals(date) && a.getStartTime() == time) {
                return true;
            }
        }
        return false;
    }
    
    public boolean isDeleted(String date, int time, List<Appointment> deleted, User user) {
        for (Appointment a : deleted) {
            if (a.getStartDay().equals(date) && a.getStartTime() == time && user.getLastname().equals(a.getName())) {
                return true;
            }
        }
        return false;
    }

    public boolean isBooked(String date, int time, List<Appointment> taken, List<Appointment> deleted) {
        for (Appointment a : taken) {
            if (isValid(time, date, a, deleted)) {
                return true;
            }
        }
        return false;
    }

    public boolean isValid(int time, String date, Appointment app, List<Appointment> delapps) {
        for (Appointment a : delapps) {
            if (a.getStartDay().equals(date) && a.getStartTime() == time && a.getName().equals(app.getName())) {
                return false;
            }
        }
        if (app.getRepeat().equals("None")) {
            if ((Integer.parseInt(app.getStartDay().split("/")[0]) == Integer.parseInt(date.split("/")[0])
                    && Integer.parseInt(app.getStartDay().split("/")[1]) == Integer.parseInt(date.split("/")[1])
                    && Integer.parseInt(app.getStartDay().split("/")[2]) == Integer.parseInt(date.split("/")[2]))
                    && (Integer.parseInt(app.getEndDay().split("/")[0]) == Integer.parseInt(date.split("/")[0])
                    && Integer.parseInt(app.getEndDay().split("/")[1]) == Integer.parseInt(date.split("/")[1])
                    && Integer.parseInt(app.getEndDay().split("/")[2]) == Integer.parseInt(date.split("/")[2]))) {
                if (app.getStartTime() <= time && app.getEndTime() > time) {
                    return true;
                } else {
                    return false;
                }
            }
        }

        if (app.getRepeat().equals("Daily")) {
            if ((Integer.parseInt(app.getStartDay().split("/")[0]) <= Integer.parseInt(date.split("/")[0])
                    && Integer.parseInt(app.getStartDay().split("/")[1]) <= Integer.parseInt(date.split("/")[1])
                    && Integer.parseInt(app.getStartDay().split("/")[2]) <= Integer.parseInt(date.split("/")[2]))
                    && (Integer.parseInt(app.getEndDay().split("/")[0]) >= Integer.parseInt(date.split("/")[0])
                    && Integer.parseInt(app.getEndDay().split("/")[1]) >= Integer.parseInt(date.split("/")[1])
                    && Integer.parseInt(app.getEndDay().split("/")[2]) >= Integer.parseInt(date.split("/")[2]))) {
                if (app.getStartTime() <= time && app.getEndTime() > time) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        if (app.getRepeat().equals("Monthly")) {
            if ((Integer.parseInt(app.getStartDay().split("/")[0]) <= Integer.parseInt(date.split("/")[0])
                    && Integer.parseInt(app.getStartDay().split("/")[1]) == Integer.parseInt(date.split("/")[1])
                    && Integer.parseInt(app.getStartDay().split("/")[2]) <= Integer.parseInt(date.split("/")[2]))
                    && (Integer.parseInt(app.getEndDay().split("/")[0]) >= Integer.parseInt(date.split("/")[0])
                    && Integer.parseInt(app.getEndDay().split("/")[1]) == Integer.parseInt(date.split("/")[1])
                    && Integer.parseInt(app.getEndDay().split("/")[2]) >= Integer.parseInt(date.split("/")[2]))) {
                if (app.getStartTime() <= time && app.getEndTime() > time) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;

    }

    public boolean checkDate2(String startDay, String endDay, String curDay, String repeat) {
        switch (repeat) {
            case "None":
                if (startDay.equalsIgnoreCase(curDay)) {
                    return true;
                }
            case "Daily":
                if (daysBetween(startDay, curDay) >= 0 && daysBetween(curDay, endDay) >= 0) {
                    return true;
                }
            case "Monthly":
                if (daysBetween(startDay, curDay) >= 0 && daysBetween(curDay, endDay) >= 0 && (Integer.valueOf(curDay.split("/")[1]) == Integer.valueOf(startDay.split("/")[1]))) {
                    return true;
                }
        }
        return false;
    }

    public boolean isSlot(Appointment app, List<String>doctors) {
        for (String doc:doctors) {
            if (app.getName().equals(doc)) {
                return true;
            }
        }
        return false;
    } 
    public String getTarget(String date, int time, List<Appointment>taken) {
        for (Appointment a:taken) {
            if (time >= a.getStartTime() && time <= a.getEndTime() && daysBetween(a.getStartDay(),date) >=0 && daysBetween(date,a.getEndDay())>=0) {
                return a.getName();
            }
        }
        return null;
    }
    public long daysBetween(String date1, String date2) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        LocalDate firstDate = LocalDate.parse(date1, formatter);
        LocalDate secondDate = LocalDate.parse(date2, formatter);
        long days = ChronoUnit.DAYS.between(firstDate, secondDate);
        return days;
    }
*/
}
