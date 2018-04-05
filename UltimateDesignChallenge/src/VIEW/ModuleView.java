/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VIEW;

import CONTROLLER.ModuleController;
import MODEL.Appointment;
import MODEL.User;
import java.util.List;
import javax.swing.JScrollPane;

/**
 *
 * @author ianona
 */
public interface ModuleView {
    
    public void setController (ModuleController controller);
    
    public void setUser (User user);
    
    public void setScheduleItems (List<Appointment> apps);
    /*
        if (controller instanceof ClientController)
            sv.setItems(apps, ((ClientController)controller).getAllUsers(), curDate);
        else
            sv.setItems();
    */
    
    public void setAgendaItems (List<Appointment> apps, String date);
    
    public void buildIcon();
    
    public void updateViews (List<Appointment> apps);
    /*
        av.setItems(apps, curDate);
        if (controller instanceof ClientController)
            sv.setItems(apps, ((ClientController)controller).getAllUsers(), curDate);
    */
    
    public void initCalendar();
    
    public void refreshCalendar(int month, int year);
}
