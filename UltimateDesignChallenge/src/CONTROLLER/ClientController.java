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
        view.setScheduleItems(model.getAllAppointments());
        view.setAgendaItems(model.getAllAppointments(), date);
    }
    
    public List<Appointment> getAllAppointments(){
        return model.getAllAppointments();
    }
    
    public List<User> getAllUsers(){
        return model.getAllUsers();
    }
    
    public void updateViews () {
        view.updateViews(model.getAllAppointments());
    }
    
    @Override
    public String toString () {
        return "Succesfully attached ClientController";
    }
}
