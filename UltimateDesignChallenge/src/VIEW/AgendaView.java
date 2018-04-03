/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VIEW;

import CONTROLLER.ClientController;
import CONTROLLER.DoctorController;
import CONTROLLER.ModuleController;
import CONTROLLER.SecretaryController;
import MODEL.Appointment;
import MODEL.User;
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
public class AgendaView extends JPanel{
    private ModuleController controller;
    private List<AgendaItem> items;
    private User user;
    
    public AgendaView (ModuleController controller) {
        this.controller = controller;
        items = new ArrayList<AgendaItem>();
        setBackground(Color.white);
        setLayout(new VerticalFlowLayout(VerticalFlowLayout.LEFT, VerticalFlowLayout.TOP, 0, 0));
    }
    
    public void setUser (User user) {
        this.user = user;
    }
    
    public void setItems (List<Appointment> apps, String date) {
        for (int i = 0; i < items.size(); i++) {
            remove(items.get(i));
        }
			
        items.clear();
        
        // DOCTOR (FILTER BY NAME)
        if (controller instanceof DoctorController) {
            for (int i = 0; i < apps.size(); i++) {
                if (apps.get(i).getName().equalsIgnoreCase(user.getFirstname()) && checkDate(apps.get(i).getStartDay(), apps.get(i).getEndDay(), date, apps.get(i).getRepeat())) {
                    items.add(new AgendaItem(controller, apps.get(i)));
                }
            }
        }
            
        // SECRETARY (CAN SEE EVERYONE)
        else if (controller instanceof SecretaryController) {
                
        }
            
        // CLIENT
        else if (controller instanceof ClientController) {
                
        }
            
        // ADDS PANELS TO THE GUI
        for (int i = 0; i < items.size(); i++) {
            add(items.get(i));
        }
        
        if (items.isEmpty()) {
            items.add(AgendaItem.createEmpty());
            add(items.get(0));
        }
        
        setPreferredSize(new Dimension(390, items.size() * 40 + 30));
        
        revalidate();
        repaint();
    }
    
    public boolean checkDate (String startDay, String endDay, String curDay, String repeat) {
        switch (repeat) {
            case "None":
                if (startDay.equalsIgnoreCase(curDay))
                    return true;
            case "Daily":
                if (daysBetween(startDay, curDay) >= 0 && daysBetween(curDay, endDay) >= 0)
                    return true;
            case "Monthly":
                if (daysBetween(startDay, curDay) >= 0 && daysBetween(curDay, endDay) >= 0 && daysBetween(startDay, curDay) % 31 == 0)
                    return true;
        }
        return false;
    }
    
    public long daysBetween (String date1, String date2) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        LocalDate firstDate = LocalDate.parse(date1, formatter);
        LocalDate secondDate = LocalDate.parse(date2, formatter);
        long days = ChronoUnit.DAYS.between(firstDate, secondDate);
        return days;
    }
}
