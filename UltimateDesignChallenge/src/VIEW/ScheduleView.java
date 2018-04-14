/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VIEW;

import CONTROLLER.ClientController;
import CONTROLLER.ModuleController;
import MODEL.Appointment;
import MODEL.User;
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

    public ScheduleView(ModuleController controller) {
        this.controller = controller;
        items = new ArrayList<ScheduleItem>();
        setBackground(Color.white);
        setLayout(new VerticalFlowLayout(VerticalFlowLayout.LEFT, VerticalFlowLayout.TOP, 0, 0));
    }

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

    public void setItems(List<Appointment> apps, List<User> users, String curDate, String name) {
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
                                if (app.getName().equals(name) && user.getType().equals("DOCTOR")) {
                                    temp = app;
                                    doctor = app;
                                    first = true;
                                    type = "doctor";
                                }
                                else if (app.getName().equals(user.getFirstname()) && user.getType().equals("CLIENT")) {
                                    if(doctor != null){
                                        if (doctor.getStartTime() <= app.getStartTime() && doctor.getEndTime() >= app.getEndTime()){
                                            temp = app;
                                            first = true;
                                            type = "client";
                                        }
                                    }
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
                            if(doctor != null)
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
                                }
                                else if (app.getName().equals(user.getFirstname()) && user.getType().equals("CLIENT")) {
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
    
    public void filterItems(List<Appointment> apps, List<User> users, String curDate, String name)
    { 
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
                
                if(name.equals("All"))
                {
                    setItems(apps, users, curDate);
                }
                
                for (Appointment app : apps) {
                    
                    if (app.getName().equals(name))
                    {
                        if (app.getStartTime() == Integer.valueOf(time.replaceAll(":", "")) && (app.getStartDay().equals(curDate) || app.getEndDay().equals(curDate))) {
                            first = true;
                            temp = app;
                            System.out.println(time + " ENTERED");
                            for (User user:users) {
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
                            if (type.equals("client")){
                                type = "doctor";
                                first = true;
                            }
                            else
                                type = "nothing";
                            
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
}
