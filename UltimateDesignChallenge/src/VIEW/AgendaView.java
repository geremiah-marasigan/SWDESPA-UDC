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
                items.add(new AgendaItem(controller, app));
            }
        } // SECRETARY (CAN SEE EVERYONE)
        else if (controller instanceof SecretaryController) {
            for (Appointment app : apps) {
                items.add(new AgendaItem(controller, app));
            }
        } // CLIENT (CAN SEE ALL HIS/HER APPOINTMENTS)
        else if (controller instanceof ClientController) {
            for (Appointment app : apps) {
                if (app.getTaken().equals(user.getLastname())) {
                    items.add(new AgendaItem(controller, app));
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
            if (controller instanceof SecretaryController) {
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

                List<Appointment> apps = ((DoctorController) controller).getSlots(info[0] + "/" + info[1] + "/" + info[2], user);
                for (Appointment app : apps) {
                    items.add(new AgendaItem(controller, app));
                }
            }

        } // SECRETARY (CAN SEE EVERYONE)
        else if (controller instanceof SecretaryController) {
            System.out.println("Sammie");
            for (String[] info : weekInfo) {
                System.out.println("ME!");
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

                List<Appointment> apps = ((SecretaryController) controller).getSlots(info[0] + "/" + info[1] + "/" + info[2]);
                //List<Appointment> apps = ((SecretaryController)controller).getAllAppointments();
                System.out.println("Date: " + info[0] + "/" + info[1] + "/" + info[2] + "; Length: " + apps.size());
                for (Appointment app : apps) {
                    System.out.println("TEDx");
                    items.add(new AgendaItem(controller, app));
                }
            }
        }

        // ADDS PANELS TO THE GUI
        for (int i = 0; i < items.size(); i++) {
            add(items.get(i));
        }

        setPreferredSize(new Dimension(390, items.size() * 40 + 30));

        revalidate();
        repaint();
    }

    public void filterItems(List<Appointment> apps, String date, String name) {
        if (controller instanceof SecretaryController) { //SECRETARY VIEW
            for (int i = 0; i < items.size(); i++) {
                remove(items.get(i));
            }

            items.clear();

            for (int i = 0; i < apps.size(); i++) {
                System.out.println("Date is " + date);
                //if (checkDate(apps.get(i).getStartDay(), apps.get(i).getEndDay(), date, apps.get(i).getRepeat())) {
                if (apps.get(i).getDate().equals(date)) {
                    if (name.equals("All")) //if secretary selects "All" doctors view
                    {
                        items.add(new AgendaItem(controller, apps.get(i)));
                    } else if (apps.get(i).getName().equals(name)) //if secretary selects a specific doctor view
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
