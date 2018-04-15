/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VIEW;

import CONTROLLER.*;
import MODEL.*;
import java.awt.Color;
import java.awt.Dimension;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author ianona
 */
public class ScheduleView extends JPanel {

    private ModuleController controller;
    List<ScheduleItem> items;

    public ScheduleView(ModuleController controller) {
        this.controller = controller;
        items = new ArrayList<ScheduleItem>();
        setBackground(Color.white);
        setLayout(new VerticalFlowLayout(VerticalFlowLayout.LEFT, VerticalFlowLayout.TOP, 0, 0));
    }

    // LETS TRY TO ALL USE THE SAME ?
    public void setItems(List<Appointment> apps, User user) {
        for (int i = 0; i < items.size(); i++) {
            remove(items.get(i));
        }
        
        List<Integer> times = new ArrayList<>();
        for (Appointment app:apps) {
            times.add(app.getTime());
        }
        items.clear();
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

                // DOCTOR
                if (controller instanceof DoctorController) {
                    if (times.contains(timecheck))
                        items.add(new ScheduleItem(controller,time,getApps(timecheck, apps),user));
                    else
                        items.add(new ScheduleItem(controller, time));
                } // SECRETARY (CAN SEE EVERYONE)
                else if (controller instanceof SecretaryController) {

                } // CLIENT (CAN SEE ALL HIS/HER APPOINTMENTS)
                else if (controller instanceof ClientController) {

                }
            }
        }

        for (int i = 0; i < items.size(); i++) {
            add(items.get(i));
        }

        setPreferredSize(new Dimension(260, items.size() * 45 + 30));

        revalidate();
        repaint();
    }
    
    public List<Appointment> getApps(int time, List<Appointment> apps) {
        List<Appointment> temp = new ArrayList<>();
        for (Appointment app:apps) {
            if (app.getTime()==time)
                temp.add(app);
        }
        return temp;
    }

    /*
    // CLIENT
    public void setItems(List<Appointment> apps, List<User> users, List<Appointment> delete, String curDate, String name) {
        for (int i = 0; i < items.size(); i++) {
            remove(items.get(i));
        }
        items.clear();
        Appointment temp = null;
        Appointment doctor = null;
        String type = "nothing";
        outerloop:
        for (Appointment App : apps) {
            if (checkDate(App.getStartDay(), App.getEndDay(), curDate, App.getRepeat())) {
                if (users != null) {
                    for (User Users : users) {
                        if (App.getName().equals(name) && Users.getType().equals("DOCTOR")) {
                            doctor = App;
                            break outerloop;
                        }
                    }
                }
            }
        }
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
                for (int x = 0; x < apps.size(); x++) {
                    if (doctor == null) {
                        type = "nothing";
                    } else if (doctor.getEndTime() == Integer.valueOf(time.replaceAll(":", ""))) {
                        type = "nothing";
                    } else if (apps.get(x).getStartTime() == Integer.valueOf(time.replaceAll(":", "")) && checkDate(apps.get(x).getStartDay(), apps.get(x).getEndDay(), curDate, apps.get(x).getRepeat())) {
                        if (users != null) {
                            for (User user : users) {
                                if (apps.get(x).getName().equals(name) && user.getType().equals("DOCTOR")) {
                                    temp = apps.get(x);
                                    doctor = apps.get(x);
                                    type = "doctor";
                                } else {
                                    if (doctor != null) {
                                        if (doctor.getStartTime() <= apps.get(x).getStartTime() && doctor.getEndTime() >= apps.get(x).getEndTime()) {
                                            temp = apps.get(x);
                                            type = "client";
                                        }
                                    }
                                }
                            }
                        } else {
                            type = "nothing";
                        }
                        if (delete != null) {
                            for (Appointment deleted : delete) {
                                System.out.print(deleted.getStartTime() + " DELETED SHITE ");
                                if (deleted.getStartTime() == apps.get(x).getStartTime() && deleted.getEndTime() == apps.get(x).getEndTime() && checkDate(deleted.getStartDay(), deleted.getEndDay(), curDate, deleted.getRepeat())) {
                                    System.out.print(apps.get(x).getStartTime() + " HERE ");
                                    type = "doctor";
                                    temp = doctor;
                                }
                            }
                        }
                    } else if (apps.get(x).getEndTime() == Integer.valueOf(time.replaceAll(":", "")) && checkDate(apps.get(x).getStartDay(), apps.get(x).getEndDay(), curDate, apps.get(x).getRepeat())) {

                        if (x + 1 < apps.size()) {
                            if (apps.get(x + 1).getStartTime() == apps.get(x).getEndTime()&& checkDate(apps.get(x + 1).getStartDay(), apps.get(x + 1).getEndDay(), curDate, apps.get(x + 1).getRepeat())) {
                                for (User user : users) {
                                    if (apps.get(x + 1).getName().equals(name) && user.getType().equals("DOCTOR")) {
                                        temp = apps.get(x + 1);
                                        doctor = apps.get(x + 1);
                                        type = "doctor";
                                    } else {
                                        if (doctor != null) {
                                            if (doctor.getStartTime() <= apps.get(x + 1).getStartTime() && doctor.getEndTime() >= apps.get(x + 1).getEndTime()) {
                                                temp = apps.get(x + 1);
                                                type = "client";
                                            }
                                        }
                                    }
                                }
                            } else if (doctor.getEndTime() >= Integer.valueOf(time.replaceAll(":", "")) && Integer.valueOf(time.replaceAll(":", "")) >= doctor.getStartTime()) {
                                temp = doctor;
                                type = "doctor";
                            } else {
                                type = "nothing";
                            }
                        } else if (doctor.getEndTime() >= Integer.valueOf(time.replaceAll(":", "")) && Integer.valueOf(time.replaceAll(":", "")) >= doctor.getStartTime()) {
                            temp = doctor;
                            type = "doctor";
                        } else {
                            type = "nothing";
                        }

                    }
                }
                items.add(new ScheduleItem(controller, time, temp, type));
            }
        }

        for (int i = 0; i < items.size(); i++) {
            add(items.get(i));
        }

        setPreferredSize(new Dimension(260, items.size() * 45 + 30));

        revalidate();
        repaint();
    }

    public void setItems(List<Appointment> apps, List<User> users, List<Appointment> delete, String curDate) {
        for (int i = 0; i < items.size(); i++) {
            remove(items.get(i));
        }

        items.clear();
        Appointment temp = null;
        Appointment doctor = null;
        String type = "nothing";
        boolean skip = false;
        for (Appointment App : apps) {
            System.out.println(App.getName());
        }

        outerloop:
        for (Appointment App : apps) {
            if (checkDate(App.getStartDay(), App.getEndDay(), curDate, App.getRepeat())) {
                System.out.println("ENTERED CHECKDATE");
                if (users != null) {
                    for (User Users : users) {
                        if (Users.getLastname().equals(App.getName()) && Users.getType().equals("DOCTOR")) {
                            System.out.println("ENTERED DOCTOR");
                            doctor = App;
                            break outerloop;
                        }
                    }
                }
            }
        }
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

                for (int x = 0; x < apps.size(); x++) {
                    skip = false;
                    if (apps.get(x).getStartTime() == Integer.valueOf(time.replaceAll(":", "")) && checkDate(apps.get(x).getStartDay(), apps.get(x).getEndDay(), curDate, apps.get(x).getRepeat())) {
                        System.out.println("ENTERED STARTTIME");
                        if (users != null) {
                            for (User user : users) {
                                if (apps.get(x).getName().equals(user.getLastname()) && user.getType().equals("DOCTOR")) {
                                    temp = apps.get(x);
                                    doctor = apps.get(x);
                                    type = "doctor";
                                } else {
                                    temp = apps.get(x);
                                    type = "client";
                                }
                            }
                        } else {
                            type = "nothing";
                        }
                        if (delete != null) {
                            for (Appointment deleted : delete) {
                                System.out.print(deleted.getStartTime() + " DELETED SHITE ");
                                if (deleted.getStartTime() == apps.get(x).getStartTime() && deleted.getEndTime() == apps.get(x).getEndTime() && checkDate(deleted.getStartDay(), deleted.getEndDay(), curDate, deleted.getRepeat())) {
                                    System.out.print(apps.get(x).getStartTime() + " HERE ");
                                    type = "doctor";
                                    temp = doctor;
                                }
                            }
                        }
                    } else if (apps.get(x).getEndTime() == Integer.valueOf(time.replaceAll(":", "")) && checkDate(apps.get(x).getStartDay(), apps.get(x).getEndDay(), curDate, apps.get(x).getRepeat())) {
                        System.out.println("ENTERED ENDTIME");
                        if (x + 1 < apps.size()) {
                            if (apps.get(x + 1).getStartTime() == apps.get(x).getEndTime() && checkDate(apps.get(x + 1).getStartDay(), apps.get(x + 1).getEndDay(), curDate, apps.get(x + 1).getRepeat())) {
                                for (User user : users) {
                                    if (apps.get(x + 1).getName().equals(user.getLastname()) && user.getType().equals("DOCTOR")) {
                                        temp = apps.get(x + 1);
                                        doctor = apps.get(x + 1);
                                        type = "doctor";

                                    } else {
                                        temp = apps.get(x + 1);
                                        type = "client";
                                    }
                                }
                            } else if (doctor.getEndTime() != Integer.valueOf(time.replaceAll(":", ""))) {
                                temp = doctor;
                                type = "doctor";
                            } else {
                                type = "nothing";
                            }
                        } else if (doctor.getEndTime() != Integer.valueOf(time.replaceAll(":", ""))) {
                            temp = doctor;
                            type = "doctor";
                        } else {
                            type = "nothing";
                        }
                    }
                }
                items.add(new ScheduleItem(controller, time, temp, type));
            }
        }

        for (int i = 0; i < items.size(); i++) {
            add(items.get(i));
        }

        setPreferredSize(new Dimension(260, items.size() * 45 + 30));

        revalidate();
        repaint();
    }

    public boolean checkDate(String startDay, String endDay, String curDay, String repeat) {
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
                if (daysBetween(startDay, curDay) >= 0 && daysBetween(curDay, endDay) >= 0 && daysBetween(startDay, curDay) % 31 == 0) {
                    return true;
                }
        }
        return false;
    }

    public long daysBetween(String date1, String date2) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        LocalDate firstDate = LocalDate.parse(date1, formatter);
        LocalDate secondDate = LocalDate.parse(date2, formatter);
        long days = ChronoUnit.DAYS.between(firstDate, secondDate);
        return days;
    }
    // CLIENT ENDS HERE

    // SECRETARY
    public void setItems(List<Appointment> apps, List<User> users, String curDate) {
        for (int i = 0; i < items.size(); i++) {
            remove(items.get(i));
        }

        items.clear();
        boolean first = false;
        Appointment temp = null;
        Appointment doctor = null;
        String type = "nothing";
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

                for (Appointment app : apps) {
                    if (app.getStartTime() == Integer.valueOf(time.replaceAll(":", "")) && (app.getStartDay().equals(curDate) || app.getEndDay().equals(curDate))) {
                        if (users != null) {
                            for (User user : users) {
                                if (app.getName().equals(user.getFirstname()) && user.getType().equals("DOCTOR")) {
                                    temp = app;
                                    doctor = app;
                                    first = true;
                                    type = "doctor";
                                } else if (app.getName().equals(user.getFirstname()) && user.getType().equals("CLIENT")) {
                                    temp = app;
                                    first = true;
                                    type = "client";
                                }
                            }
                        } else {
                            type = "nothing";
                        }
                        break;
                    } else if (app.getEndTime() == Integer.valueOf(time.replaceAll(":", "")) && (app.getStartDay().equals(curDate) || app.getEndDay().equals(curDate))) {
                        System.out.println(time + " LEFT");
                        if (type.equals("client")) {
                            type = "doctor";
                            first = true;
                            temp = doctor;
                            break;
                        } else {
                            type = "nothing";
                        }

                    } else {
                        first = false;
                    }
                }
                items.add(new ScheduleItem(controller, time, first, temp, type));
            }
        }

        for (int i = 0; i < items.size(); i++) {
            add(items.get(i));
        }

        setPreferredSize(new Dimension(260, items.size() * 45 + 30));

        revalidate();
        repaint();
    }
    // SECRETARY ENDS HERE

    public void filterItems(List<Appointment> apps, List<User> users, String curDate, String name) {
        for (int i = 0; i < items.size(); i++) {
            remove(items.get(i));
        }

        items.clear();
        boolean first = false;
        Appointment temp = null;
        String type = "nothing";
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

                if (name.equals("All")) {
                    setItems(apps, users, curDate);
                }

                for (Appointment app : apps) {

                    if (app.getName().equals(name)) {
                        if (app.getStartTime() == Integer.valueOf(time.replaceAll(":", "")) && (app.getStartDay().equals(curDate) || app.getEndDay().equals(curDate))) {
                            first = true;
                            temp = app;
                            System.out.println(time + " ENTERED");
                            for (User user : users) {
                                if (app.getName().equals(user.getFirstname()) && user.getType().equals("DOCTOR")) {
                                    type = "doctor";
                                }
                                if (app.getName().equals(user.getFirstname()) && user.getType().equals("CLIENT")) {
                                    type = "client";
                                }
                            }
                            break;
                        } else if (app.getEndTime() == Integer.valueOf(time.replaceAll(":", "")) && (app.getStartDay().equals(curDate) || app.getEndDay().equals(curDate))) {
                            System.out.println(time + " LEFT");
                            if (type.equals("client")) {
                                type = "doctor";
                                first = true;
                            } else {
                                type = "nothing";
                            }

                        } else {
                            first = false;
                        }
                    }
                }
                items.add(new ScheduleItem(controller, time, first, temp, type));
            }
        }

        for (int i = 0; i < items.size(); i++) {
            add(items.get(i));
        }

        setPreferredSize(new Dimension(260, items.size() * 45 + 30));

        revalidate();
        repaint();

    }

    // GENERIC
    public void setItems() {
        for (int i = 0; i < items.size(); i++) {
            remove(items.get(i));
        }

        items.clear();

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
                items.add(new ScheduleItem(controller, time));
            }
        }

        for (int i = 0; i < items.size(); i++) {
            add(items.get(i));
        }

        setPreferredSize(new Dimension(260, items.size() * 45 + 30));

        revalidate();
        repaint();
    }
     */
}
