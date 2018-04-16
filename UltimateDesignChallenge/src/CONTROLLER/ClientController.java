/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROLLER;

import MODEL.Appointment;
import MODEL.ClientService;
import MODEL.ModuleService;
import MODEL.User;
import VIEW.ClientView;
import VIEW.ModuleView;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author ianona
 */
public class ClientController extends ModuleController {
    
    public ClientController(ModuleService model, ModuleView view) {
        super(model, view);
    }
    
    public void start () {
        GregorianCalendar cal = new GregorianCalendar();
        int dayBound = cal.get(GregorianCalendar.DAY_OF_MONTH);
        int monthBound = cal.get(GregorianCalendar.MONTH) + 1;
        int yearBound = cal.get(GregorianCalendar.YEAR);
        String date = monthBound + "/" + dayBound + "/" + yearBound;
        
        view.setController(this);
        
        updateViews(date);
    }
    
    public void addAppointment(Appointment app) {
        ((ClientService)model).addAppointment(app);
        updateViews(app.getDate());
    }
    
    public void deleteDay(String date, String name) {
        ((ClientService)model).deleteDay(date,name);
        updateViews(date);
    }
    
    public void deleteAppointment(Appointment app) {
        ((ClientService)model).deleteAppointment(app);
        updateViews(app.getDate());
    }
    
    public void edit(String time) {
        ((ClientView)view).edit(time);
    }
    
    public List<Appointment> getAllAppointments(){
        return model.getAllAppointments();
    }
    public List<Appointment> getAllFree(String date){
        return ((ClientService)model).getAllFreeAppointments(date);
    }
    public List<User> getAllUsers(){
        return model.getAllUsers();
    }
    
    public List<Appointment> getAllFilter(String name, String date){
        if (name.equals("All Doctors"))
            return ((ClientService)model).getAllFreeAppointments(date);
        else
            return ((ClientService)model).getAllFilter(name,date);
    }
    
    public void updateViews (String date) {
        view.updateViews(model.getSlots(date));
    }
}
