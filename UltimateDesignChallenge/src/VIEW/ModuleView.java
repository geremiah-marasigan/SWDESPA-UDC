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

/**
 *
 * @author ianona
 */
public interface ModuleView {
    public void setController (ModuleController controller);
    
    public void setUser (User user);
    
    public void setScheduleItems (List<Appointment> apps);
    
    public void setAgendaItems (List<Appointment> apps, String date);
    
    public void buildIcon();
    
    public void updateViews (List<Appointment> apps);
    
    public void initCalendar();
    
    public void refreshCalendar(int month, int year);
}
