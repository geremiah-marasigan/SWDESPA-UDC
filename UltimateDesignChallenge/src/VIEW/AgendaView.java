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
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
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

    public void setItems(List<Appointment> apps, String date) {
        for (int i = 0; i < items.size(); i++) {
            remove(items.get(i));
        }

        items.clear();
        boolean skip = false;

        // DOCTOR (FILTER BY NAME)
        if (controller instanceof DoctorController) {
            Director director = new Director();
            director.setTimeslotBuilder(new AppointmentSlotBuilder(), controller);
            List<Appointment> delapps = ((DoctorController) controller).getDeletedSlots();
            List<Appointment> taken = ((DoctorController) controller).getAppointments();
            director.addAgendaPanels(items, apps, user, date, delapps, taken);
        } // SECRETARY (CAN SEE EVERYONE)
        else if (controller instanceof SecretaryController) {
            for (int i = 0; i < apps.size(); i++) {
                System.out.println("Sammie is " + date);
                if (checkDate(apps.get(i).getStartDay(), apps.get(i).getEndDay(), date, apps.get(i).getRepeat())) {
                    items.add(new AgendaItem(controller, apps.get(i)));
                }
            }
        } // CLIENT (CAN SEE ALL HIS/HER APPOINTMENTS)
        else if (controller instanceof ClientController) {
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

    public boolean notDeleted(Appointment app, String date, List<Appointment> delapps) 
    {
        for (Appointment a : delapps) {
            if (date.equalsIgnoreCase(a.getStartDay()) && app.getStartTime() == a.getStartTime() && app.getEndTime() == a.getEndTime()) {
                return false;
            }
        }
        return true;
    }
    
    public void filterItems(List<Appointment> apps, String date, String name)
    {       
        if (controller instanceof SecretaryController) { //SECRETARY VIEW
            for (int i = 0; i < items.size(); i++) {
               remove(items.get(i));
            }

            items.clear();           
            
            for (int i = 0; i < apps.size(); i++) {
                System.out.println("Date is " + date);
                if (checkDate(apps.get(i).getStartDay(), apps.get(i).getEndDay(), date, apps.get(i).getRepeat())) {
                    if(name.equals("All")) //if secretary selects "All" doctors view
                    {
                         items.add(new AgendaItem(controller, apps.get(i)));
                    }
                    else if (apps.get(i).getName().equals(name)) //if secretary selects a specific doctor view
                         {
                            items.add(new AgendaItem(controller, apps.get(i)));
                         }
                }     
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
