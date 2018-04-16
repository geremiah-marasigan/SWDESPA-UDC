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
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 *
 * @author ianona
 */
public class AgendaView extends JPanel {

    private ModuleController controller;
    private List<AgendaItem> items;
    private User user;

    public AgendaView(ModuleController controller) {
        this.controller = controller;
        items = new ArrayList<AgendaItem>();
        setBackground(Color.white);
        setLayout(new VerticalFlowLayout(VerticalFlowLayout.LEFT, VerticalFlowLayout.TOP, 0, 0));
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setItems(List<Appointment> apps) {
        for (int i = 0; i < items.size(); i++) {
            remove(items.get(i));
        }

        items.clear();

        // DOCTOR (FILTER BY NAME)
        if (controller instanceof DoctorController) {
            for (Appointment app : apps) {
                if (app.getName().equals(user.getLastname())) {
                    items.add(new AgendaItem(controller, app));
                }
            }
        } // SECRETARY (CAN SEE EVERYONE)
        else if (controller instanceof SecretaryController) {
            /*
            for (int i = 0; i < apps.size(); i++) {
                System.out.println("Sammie is " + date);
                if (checkDate(apps.get(i).getStartDay(), apps.get(i).getEndDay(), date, apps.get(i).getRepeat())) {
                    items.add(new AgendaItem(controller, apps.get(i)));
                }
            }
             */
        } // CLIENT (CAN SEE ALL HIS/HER APPOINTMENTS)
        else if (controller instanceof ClientController) {
            for(Appointment app :apps){
                if (app.getTaken().equals("NOT_TAKEN"))
                    items.add(new AgendaItem(controller, app));
            }
            /*
            List<User> tempUsers = ((ClientController) controller).getAllUsers();
            List<Appointment> delete = ((ClientController) controller).getAllDeleted();
            for (Appointment app : apps) {
                skip = false;
                for (User user : tempUsers) {
                    if (app.getName().equals(this.user.getLastname()) && checkDate(app.getStartDay(), app.getEndDay(), date, app.getRepeat())) {
                        for (Appointment deleted : delete) {
                            if (deleted.getStartTime() == app.getStartTime() && deleted.getEndTime() == app.getEndTime() && checkDate(deleted.getStartDay(), deleted.getEndDay(), date, deleted.getRepeat())) {
                                skip = true;
                            }
                        }
                        if (!skip) {
                            items.add(new AgendaItem(controller, app, date));
                            break;
                        }
                    }
                }
            }
             */
        }

        // ADDS PANELS TO THE GUI
        for (int i = 0; i < items.size(); i++) {
            add(items.get(i));
        }

        if (items.isEmpty()) {
            if (controller instanceof DoctorController) {
                items.add(AgendaItem.createEmptyDoctor());
            }
            if (controller instanceof ClientController) {
                items.add(AgendaItem.createEmptyClient());
            }
            add(items.get(0));
        }

        setPreferredSize(new Dimension(390, items.size() * 40 + 30));

        revalidate();
        repaint();
    }

    public void setWeeklyItems(List<String[]> weekInfo) {
        for (int i = 0; i < items.size(); i++) {
            remove(items.get(i));
        }

        items.clear();

        // DOCTOR
        if (controller instanceof DoctorController) {
            for (String[] info : weekInfo) {
                SimpleDateFormat simpleDateformat = new SimpleDateFormat("E"); // the day of the week abbreviated

                String startDateString = info[0] + "/" + info[1] + "/" + info[2];
                DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                Date startDate;
                
                try {
                    startDate = df.parse(startDateString);
                    items.add(AgendaItem.createTitle(simpleDateformat.format(startDate) + ": " + info[0] + "/" + info[1] + "/" + info[2]));
                } catch (ParseException ex) {
                    Logger.getLogger(WeeklyAgendaView.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                List<Appointment> apps = ((DoctorController) controller).getSlots(info[0] + "/" + info[1] + "/" + info[2]);
                for (Appointment app : apps) {
                    if (app.getName().equals(user.getLastname())) {
                        items.add(new AgendaItem(controller, app));
                    }
                }
            }

        } // SECRETARY (CAN SEE EVERYONE)
        else if (controller instanceof SecretaryController) {
        }

        // ADDS PANELS TO THE GUI
        for (int i = 0; i < items.size(); i++) {
            add(items.get(i));
        }

        setPreferredSize(new Dimension(390, items.size() * 40 + 30));

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
                if (daysBetween(startDay, curDay) >= 0 && daysBetween(curDay, endDay) >= 0 && (Integer.valueOf(curDay.split("/")[1]) == Integer.valueOf(startDay.split("/")[1]))) {
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

    
    public void filterItems(List<Appointment> apps, String date, String name) {
        if (controller instanceof SecretaryController) { //SECRETARY VIEW
            for (int i = 0; i < items.size(); i++) {
                remove(items.get(i));
            }

            items.clear();

            for (int i = 0; i < apps.size(); i++) {
                System.out.println("Date is " + date);
                
            }

            // ADDS PANEL TO THE GUI
            for (int i = 0; i < items.size(); i++) {
                add(items.get(i));
            }

            revalidate();
            repaint();
        }
    }
}
