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
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author ianona
 */
public class ScheduleView extends JPanel {

    private ModuleController controller;
    List<ScheduleItem> items;
    private User user;

    public ScheduleView(ModuleController controller) {
        this.controller = controller;
        items = new ArrayList<ScheduleItem>();
        setBackground(Color.white);
        setLayout(new VerticalFlowLayout(VerticalFlowLayout.LEFT, VerticalFlowLayout.TOP, 0, 0));
    }

    public void setUser(User user) {
        this.user = user;
    }

    // LETS TRY TO ALL USE THE SAME ?
    public void setItems(List<Appointment> apps) {
        for (int i = 0; i < items.size(); i++) {
            remove(items.get(i));
        }

        List<Integer> times = new ArrayList<>();
        for (Appointment app : apps) {
            times.add(app.getTime());
        }
        items.clear();
        for (int hour = 9; hour < 21; hour++) {
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
                    if (times.contains(timecheck)) {
                        items.add(new ScheduleItem(controller, time, getApp(timecheck, apps)));
                    } else {
                        items.add(new ScheduleItem(controller, time));
                    }
                } // SECRETARY (CAN SEE EVERYONE)
                else if (controller instanceof SecretaryController) {
                    if (times.contains(timecheck)) {
                        items.add(new ScheduleItem(controller, time, getApps(timecheck, apps)));
                    } else {
                        items.add(new ScheduleItem(controller, time));
                    }
                } // CLIENT (CAN SEE ALL HIS/HER APPOINTMENTS)
                else if (controller instanceof ClientController) {

                    if (times.contains(timecheck)) {
                        items.add(new ScheduleItem(controller, time, getApps(timecheck, apps), user));
                    } else {
                        items.add(new ScheduleItem(controller, time));
                    }
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

                // SECRETARY (CAN SEE EVERYONE)
                if (controller instanceof SecretaryController) {
                    if (times.contains(timecheck))
                        items.add(new ScheduleItem(controller,time,getApps(timecheck, apps)));
                    else
                        items.add(new ScheduleItem(controller, time));
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

    public Appointment getApp(int time, List<Appointment> apps) {
        for (Appointment app : apps) {
            if (app.getTime() == time) {
                return app;
            }
        }
        return null;
    }

    public List<Appointment> getApps(int time, List<Appointment> apps) {
        List<Appointment> temp = new ArrayList<>();
        for (Appointment app : apps) {
            if (app.getTime() == time) {
                temp.add(app);
            }
        }
        return temp;
    }

    public List<Appointment> getApps(int time, List<Appointment> apps, String name) {
        List<Appointment> temp = new ArrayList<>();
        for (Appointment app : apps) {
            if (app.getTime() == time) {
                if (app.getName().equals(name)) {
                    temp.add(app);
                }
            }
        }
        return temp;
    }

    public void filterItems(List<Appointment> apps, List<User> users, String curDate, String name) {
        User doc = null;

        System.out.println("Entered Sched View filterItems()");

        if (controller instanceof SecretaryController) {

            for (int i = 0; i < items.size(); i++) {
                remove(items.get(i));
            }

            items.clear();

            List<Integer> times = new ArrayList<>();
            for (Appointment app : apps) {
                times.add(app.getTime());
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
                    int timecheck = Integer.valueOf(time.replace(":", ""));

                    for (int i = 0; i < apps.size(); i++) {
                        if (apps.get(i).getName().equals(name)) {
                            for (int j = 0; j < users.size(); j++) {
                                if (users.get(j).getLastname().equals(name)) {
                                    doc = users.get(j);
                                }
                            }
                        }
                    }

                    if (times.contains(timecheck)) {
                        items.add(new ScheduleItem(controller, time, getApps(timecheck, apps, name)));
                    } else {
                        items.add(new ScheduleItem(controller, time));
                    }
                }
            }
        }

        for (int x = 0; x < items.size(); x++) {
            add(items.get(x));
        }

        setPreferredSize(new Dimension(260, items.size() * 45 + 30));

        revalidate();
        repaint();
    }
}
