/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CONTROLLER;

import MODEL.Appointment;
import MODEL.DoctorService;
import MODEL.ModuleService;
import VIEW.ModuleView;
import java.util.GregorianCalendar;

/**
 *
 * @author ianona
 */
public class DoctorController extends ModuleController {
    
    public DoctorController(ModuleService model, ModuleView view) {
        super(model, view);
    }
    
    public void start () {
        view.setController(this);
        
        GregorianCalendar cal = new GregorianCalendar();
        int dayBound = cal.get(GregorianCalendar.DAY_OF_MONTH);
        int monthBound = cal.get(GregorianCalendar.MONTH) + 1;
        int yearBound = cal.get(GregorianCalendar.YEAR);
        String date = monthBound + "/" + dayBound + "/" + yearBound;
        
        view.setScheduleItems(model.getAllAppointments());
        view.setAgendaItems(model.getAllAppointments(), date);
    }
    
    public void addAppointment(Appointment app) {
        ((DoctorService)model).addAppointment(app);
        updateViews();
    }
    
    
    public void deleteAppointment(Appointment app) {
        ((DoctorService)model).deleteAppointment(app);
        updateViews();
    }
    
    public void updateViews () {
        view.updateViews(model.getAllAppointments());
    }
    
    @Override
    public String toString () {
        return "Succesfully attached DoctorController";
    }
}
